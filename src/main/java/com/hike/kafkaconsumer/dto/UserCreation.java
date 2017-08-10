package com.hike.kafkaconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author siddharthasingh
 */
public class UserCreation {

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
