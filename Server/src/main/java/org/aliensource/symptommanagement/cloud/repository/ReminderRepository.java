package org.aliensource.symptommanagement.cloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ttruong on 16-Dec-14.
 */
@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Long> {

    public List<Reminder> findByPatientId(long patientId);

}
