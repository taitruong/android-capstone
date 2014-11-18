package org.aliensource.symptommanagement.cloud.repository;

import org.aliensource.symptommanagement.cloud.service.DoctorSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the repository through a controller and map it to the
// svc path.
//
@RepositoryRestResource(path = DoctorSvcApi.SVC_PATH)
public interface DoctorRepository extends CrudRepository<Doctor, Long>{

    public Doctor findByUsername(
            @Param(ServiceUtils.PARAMETER_USERNAME) String username);
}
