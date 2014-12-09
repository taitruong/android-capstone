package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.CheckInRepository;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.aliensource.symptommanagement.cloud.repository.TimestampComparator;
import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class CheckInController {

    @Autowired
    private CheckInRepository repository;

    @RequestMapping(value= CheckInSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<CheckIn> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= CheckInSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    CheckIn findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    /**
     * Retrieves all check-ins from a patient and sort it by timestamp
     * @param patientId
     * @return
     */
    @RequestMapping(value= CheckInSvcApi.SEARCH_PATH_PATIENT, method= RequestMethod.GET)
    public @ResponseBody
    List<CheckIn> findByPatientId(@PathVariable(ServiceUtils.PARAMETER_ID) long patientId) {
        List<CheckIn> result = repository.findByPatientId(patientId);
        result.sort(new TimestampComparator<CheckIn>());
        return result;
    }

    @RequestMapping(value=CheckInSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody CheckIn save(@RequestBody CheckIn model) {
        return repository.save(model);
    }
}
