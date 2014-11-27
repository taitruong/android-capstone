package org.aliensource.symptommanagement.cloud.repository;

import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the repository through a controller and map it to the
// svc path.
//
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>{

    public Patient findByUsername(
            @Param(ServiceUtils.PARAMETER_USERNAME) String username);
}
