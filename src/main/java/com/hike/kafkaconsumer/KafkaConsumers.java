package com.hike.kafkaconsumer;

import com.hike.kafkaconsumer.config.ConsumerConfiguration;
import com.hike.kafkaconsumer.config.KafkaConsumerConfiguration;
import com.hike.kafkaconsumer.observer.Observer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author siddharthasingh
 */
public class KafkaConsumers extends Observer implements Runnable {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumers.class);
    private final KafkaConsumer<String, String> consumer;
    private final List<String> topics;
    private final int id;
    
    public KafkaConsumers(int id, KafkaConsumerConfiguration configuration) {
        this.id = id;
        ConsumerConfiguration consumerConfiguration = configuration.getConsumer();
        this.topics = Arrays.asList(consumerConfiguration.getTopics());
        Properties props = new Properties();
        String serverPort = consumerConfiguration.getConsumerProperties().getServer()
            + ":" + consumerConfiguration.getConsumerProperties().getPort();
        props.put("bootstrap.servers", serverPort);
        props.put("group.id", configuration.getConsumer().getGroupId());
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("session.timeout.ms", configuration.getConsumer().getConsumerProperties().getSessionTimeOutMs());

//auto.offset.reset
//enable.auto.commit
//auto.commit.interval.ms
        this.consumer = new KafkaConsumer<>(props);
        LOGGER.info("Properties for consumer - {} is  '{}'", id, props);
    }
    
    @Override
    public void run() {
        try {
            consumer.subscribe(topics);
            
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    //<TODO> Remove sout line once ready to ship.
                    System.out.println(this.id + ": " + data);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Consumer - {} data '{}", this.id, data);
                    }
                }
            }
        }
        catch (WakeupException e) {
            if (e.getMessage() == null) {
                LOGGER.info("WakeupException called by shutdown ", e);
            }
            // ignore for shutdown
        }
        finally {
            LOGGER.info("closing consumer...");
            consumer.close();
        }
    }
    
    public void shutdown() {
        LOGGER.info("shutdown called...");
        consumer.wakeup();
    }

    @Override
    public void shutConsumer() {
        this.shutdown();
    }

}
