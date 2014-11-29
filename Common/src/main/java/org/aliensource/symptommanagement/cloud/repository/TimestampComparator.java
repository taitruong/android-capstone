package org.aliensource.symptommanagement.cloud.repository;

import java.util.Comparator;

/**
 * Created by ttruong on 29-Nov-14.
 */
public class TimestampComparator<T extends BaseTimestampModel> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return o1.getTimestamp() < o2.getTimestamp() ? - 1 : (o1.getTimestamp() == o2.getTimestamp() ? 0 : 1);
    }

}
