package org.aliensource.symptommanagement.cloud.repository;

import java.util.Comparator;

/**
 * Created by ttruong on 29-Nov-14.
 */
public class LastNameComparator<T extends Person> implements Comparator<Person> {

    public int compare(Person o1, Person o2) {
        return o1.getLastName().compareTo(o2.getLastName());
    }

}
