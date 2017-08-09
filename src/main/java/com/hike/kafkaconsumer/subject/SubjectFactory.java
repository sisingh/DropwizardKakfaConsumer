package com.hike.kafkaconsumer.subject;

import com.hike.kafkaconsumer.enums.SubjectEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author siddharthasingh
 */
public class SubjectFactory {

    private static final Map<SubjectEnum, Subject> subjectMap;

    static {
        subjectMap = new HashMap();
        subjectMap.put(SubjectEnum.KAFKA_DW_CONSUMER, new Subject());
    }

    public static Subject getSubject(SubjectEnum subjectEnum) {
        return subjectMap.get(subjectEnum);
    }
}
