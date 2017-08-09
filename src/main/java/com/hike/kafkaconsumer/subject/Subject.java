package com.hike.kafkaconsumer.subject;

import com.hike.kafkaconsumer.observer.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author siddharthasingh
 */
public class Subject {
    public List<Observer> observers = new ArrayList<>();

    public void add(Observer o) {
        observers.add(o);
    }

    public void changeConsumerState(boolean toStop) {
        if (toStop == true) {
            observers.forEach((ob) -> {
                ob.shutConsumer();
            });
        }
    }
}
