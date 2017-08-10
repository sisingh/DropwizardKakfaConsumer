package com.hike.kafkaconsumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author siddharthasingh
 */
public class KafkaConsumerConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    @JsonProperty("swagger")
    public void setSwaggerBundleConfiguration(SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }

    @Valid
    @NotNull
    private ConsumerConfiguration consumerConfiguration = new ConsumerConfiguration();

    @JsonProperty("kafkaConsumer")
    public void setConsumer(ConsumerConfiguration consumerConfiguration) {
        this.consumerConfiguration = consumerConfiguration;
    }

    @JsonProperty("kafkaConsumer")
    public ConsumerConfiguration getConsumer() {
        return this.consumerConfiguration;
    }

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @Valid
    @NotNull
    @JsonProperty("services")
    private Services services;

    @JsonProperty("services")
    public void setServices(Services services) {
        this.services = services;
    }

    @JsonProperty("services")
    public Services getServices() {
        return services;
    }

}
