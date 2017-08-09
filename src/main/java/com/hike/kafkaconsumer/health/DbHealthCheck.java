package com.hike.kafkaconsumer.health;

/**
 *
 * @author siddharthasingh
 */
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;

public class DbHealthCheck extends HealthCheck {

//    public TemplateHealthCheck(Template template) {
//        this.template = template;
//    }
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
