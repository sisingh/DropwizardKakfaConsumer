package com.hike.kafkaconsumer;

/**
 *
 * @author siddharthasingh
 */
import com.hike.kafkaconsumer.config.KafkaConsumerConfiguration;
import com.hike.kafkaconsumer.config.Services;
import com.hike.kafkaconsumer.enums.SubjectEnum;
import com.hike.kafkaconsumer.resources.ShutDownHookApi;
import com.hike.kafkaconsumer.resources.UserAPI;
import com.hike.kafkaconsumer.subject.Subject;
import com.hike.kafkaconsumer.subject.SubjectFactory;
import com.hike.rdbms.dao.UserDAO;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.servlets.tasks.LogConfigurationTask;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerLoop extends Application<KafkaConsumerConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerLoop.class);

    public static void main(String[] args) throws Exception {
        new KafkaConsumerLoop().run(args);
    }

    final List<KafkaConsumers> consumers = new ArrayList<>();

    private final Subject subject = SubjectFactory.getSubject(SubjectEnum.KAFKA_DW_CONSUMER);

    ExecutorService executor = null;

    @Override
    public String getName() {
        return "Kafka Dropwizard Consumer";
    }

    @Override
    public void initialize(Bootstrap<KafkaConsumerConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.addBundle(new SwaggerBundle<KafkaConsumerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(KafkaConsumerConfiguration configuration) {
                LOGGER.info("#####################Swagger bundle created#####################");
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(KafkaConsumerConfiguration configuration, Environment environment) throws ClassNotFoundException {

        // curl -X POST -d "logger=com.hike.kafkaconsumer.resources&level=" http://localhost:8112/tasks/log-level
        environment.admin().addTask(new LogConfigurationTask());
//        environment.admin().addTask(new EchoTask());
        environment.jersey().register(new ShutDownHookApi());

        Services services = configuration.getServices();
        if (services.getKafkaConsumer()) {
            start(configuration, environment);
        }

        if (services.getDatabase()) {
            final DBIFactory factory = new DBIFactory();
            final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
            final UserDAO dao = jdbi.onDemand(UserDAO.class);
            environment.jersey().register(new UserAPI(dao));
        }

        JVMShutDownHook(configuration);
    }

    private void start(KafkaConsumerConfiguration configuration, Environment environment) {
        int numConsumers = configuration.getConsumer().getNumberOfConsumers();

        executor = Executors.newFixedThreadPool(numConsumers);

        for (int i = 0; i < numConsumers; i++) {
            KafkaConsumers consumer = new KafkaConsumers(i, configuration);
            consumers.add(consumer);
            subject.add(consumer);
            executor.submit(consumer);
        }
    }

    private void JVMShutDownHook(KafkaConsumerConfiguration configuration) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOGGER.info("JVMShutDownHook called...");

                if (executor != null) {
                    consumers.forEach((consumer) -> {
                        consumer.shutdown();
                    });
                    executor.shutdown();
                    try {
                        executor.awaitTermination(configuration.getConsumer().getConsumerTerminationTimout(),
                            TimeUnit.MILLISECONDS);
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        });
    }
}
