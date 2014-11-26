package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.MedicationRepository;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;
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
public class MedicationController {

    @Autowired
    private MedicationRepository repository;

    @RequestMapping(value= MedicationSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Medication> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= MedicationSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Medication findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value=MedicationSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Medication save(@RequestBody Medication model) {
        return repository.save(model);
    }
}
