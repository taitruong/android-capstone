package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Reminder;
import org.aliensource.symptommanagement.cloud.repository.ReminderRepository;
import org.aliensource.symptommanagement.cloud.repository.TimestampComparator;
import org.aliensource.symptommanagement.cloud.service.ReminderSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class ReminderController {

    @Autowired
    private ReminderRepository repository;

    @RequestMapping(value= ReminderSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Reminder> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= ReminderSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Reminder findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    /**
     * Retrieves all check-ins from a patient and sort it by timestamp
     * @param patientId
     * @return
     */
    @RequestMapping(value= ReminderSvcApi.SEARCH_PATH_PATIENT, method= RequestMethod.GET)
    public @ResponseBody
    List<Reminder> findByPatientId(@PathVariable(ServiceUtils.PARAMETER_ID) long patientId) {
        List<Reminder> result = repository.findByPatientId(patientId);
        result.sort(new TimestampComparator<Reminder>());
        return result;
    }

}
