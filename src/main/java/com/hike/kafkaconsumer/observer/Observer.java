package com.hike.kafkaconsumer.observer;

import com.hike.kafkaconsumer.subject.Subject;

/**
 *
 * @author siddharthasingh
 */
public abstract class Observer {

    protected Subject subject;

    public abstract void shutConsumer();
}
