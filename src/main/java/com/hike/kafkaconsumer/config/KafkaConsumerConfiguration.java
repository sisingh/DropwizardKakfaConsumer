package com.hike.kafkaconsumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
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

}
