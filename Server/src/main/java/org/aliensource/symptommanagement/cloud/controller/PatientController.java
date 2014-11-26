package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

import javax.xml.ws.Service;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @RequestMapping(value=PatientSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Patient> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= PatientSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Patient findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value=PatientSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Patient save(@RequestBody Patient model) {
        return repository.save(model);
    }

    @RequestMapping(value=PatientSvcApi.SEARCH_PATH_USERNAME, method=RequestMethod.GET)
    public @ResponseBody List<Patient> findByUsername(@RequestParam(ServiceUtils.PARAMETER_USERNAME) String username) {
        return repository.findByUsername(username);
    }
}
