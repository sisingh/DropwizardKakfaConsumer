package com.hike.kafkaconsumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author siddharthasingh
 */
public class Services {

    @JsonProperty("database")
    private Boolean database;

    @JsonProperty("kafkaConsumer")
    private Boolean kafkaConsumer;

    public Boolean getDatabase() {
        return database;
    }

    public void setDatabase(Boolean database) {
        this.database = database;
    }

    public Boolean getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void setKafkaConsumer(Boolean kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

}
