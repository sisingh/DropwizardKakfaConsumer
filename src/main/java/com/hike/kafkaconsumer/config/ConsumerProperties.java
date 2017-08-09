package com.hike.kafkaconsumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author siddharthasingh
 */
public class ConsumerProperties {

    @NotNull
    @NotEmpty
    @Valid
    @JsonProperty("server")
    private String server;

    @JsonProperty("server")
    public String getServer() {
        return this.server;
    }

    @JsonProperty("server")
    public void setServer(String server) {
        this.server = server;
    }

    @NotNull
    @Valid
    @JsonProperty("port")
    private Integer port;

    @JsonProperty("port")
    public Integer getPort() {
        return this.port;
    }

    @JsonProperty("port")
    public void setPort(Integer port) {
        this.port = port;
    }

    @NotNull
    @Valid
    @JsonProperty("sessionTimeOutMs")
    private Integer sessionTimeOutMs;

    @JsonProperty("sessionTimeOutMs")
    public Integer getSessionTimeOutMs() {
        return this.sessionTimeOutMs;
    }

    @JsonProperty("sessionTimeOutMs")
    public void setServer(Integer sessionTimeOutMs) {
        this.sessionTimeOutMs = sessionTimeOutMs;
    }
}
