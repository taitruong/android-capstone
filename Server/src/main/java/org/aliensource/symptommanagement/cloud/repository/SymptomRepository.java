package org.aliensource.symptommanagement.cloud.repository;

import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the repository through a controller and map it to the
// svc path.
//
@RepositoryRestResource(path = SymptomSvcApi.SVC_PATH)
public interface SymptomRepository extends CrudRepository<Symptom, Long>{

    public List<Symptom> findByType(
            @Param(ServiceUtils.PARAMETER_TYPE) String type);

}
