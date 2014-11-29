package org.aliensource.symptommanagement.cloud.repository;

import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the repository through a controller and map it to the
// svc path.
//
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>{

    public Patient findByUsername(
            @Param(ServiceUtils.PARAMETER_USERNAME) String username);

    //http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query
//    @Query("select p from Patient p where p.doctors.contains(?doctorId)")
//    public List<Patient> findByDoctorId(@Param(ServiceUtils.PARAMETER_DOCTOR_USERNAME) long doctorId);

}
