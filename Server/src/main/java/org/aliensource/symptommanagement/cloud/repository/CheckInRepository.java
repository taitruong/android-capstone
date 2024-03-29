package org.aliensource.symptommanagement.cloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the repository through a controller and map it to the
// svc path.
//
@Repository
public interface CheckInRepository extends CrudRepository<CheckIn, Long>{

    public List<CheckIn> findByPatientId(long patientId);
}
