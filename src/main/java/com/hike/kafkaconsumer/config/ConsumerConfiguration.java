package com.hike.kafkaconsumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author siddharthasingh
 */
public class ConsumerConfiguration {

    @NotNull
    @Valid
    @JsonProperty("numberOfConsumers")
    private Integer numberOfConsumers;

    @JsonProperty("numberOfConsumers")
    public void setNumberOfConsumers(Integer numberOfConsumers) {
        this.numberOfConsumers = numberOfConsumers;
    }

    @JsonProperty("numberOfConsumers")
    public Integer getNumberOfConsumers() {
        return this.numberOfConsumers;
    }

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("groupId")
    private String groupId;

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return this.groupId;
    }

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("topics")
    private String[] topics;

    @JsonProperty("topics")
    public void setGroupId(String[] topics) {
        this.topics = topics;
    }

    @JsonProperty("topics")
    public String[] getTopics() {
        return this.topics;
    }

    @NotNull
    @Valid
    @JsonProperty("consumerTerminationTimout")
    private Integer consumerTerminationTimout;

    @JsonProperty("consumerTerminationTimout")
    public Integer getConsumerTerminationTimout() {
        return consumerTerminationTimout;
    }

    @JsonProperty("consumerTerminationTimout")
    public void setConsumerTerminationTimout(Integer consumerTerminationTimout) {
        this.consumerTerminationTimout = consumerTerminationTimout;
    }

    @NotNull
    @JsonProperty("consumerProperties")
    private ConsumerProperties consumerProperties = new ConsumerProperties();

    @JsonProperty("consumerProperties")
    public ConsumerProperties getConsumerProperties() {
        return consumerProperties;
    }

    @JsonProperty("consumerProperties")
    public void setConsumerProperties(ConsumerProperties consumerProperties) {
        this.consumerProperties = consumerProperties;
    }

}
