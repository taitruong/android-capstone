package org.aliensource.symptommanagement.cloud.repository;

import java.util.Comparator;

/**
 * Created by ttruong on 29-Nov-14.
 */
public class MedicamentNameComparator implements Comparator<Medicament> {

    public int compare(Medicament o1, Medicament o2) {
        return o1.getName().compareTo(o2.getName());
    }

}
