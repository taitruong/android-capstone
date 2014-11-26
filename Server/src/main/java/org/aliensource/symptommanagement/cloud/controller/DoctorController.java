package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.DoctorRepository;
import org.aliensource.symptommanagement.cloud.service.DoctorSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @RequestMapping(value=DoctorSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Doctor> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= DoctorSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Doctor findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value=DoctorSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Doctor save(@RequestBody Doctor model) {
        return repository.save(model);
    }

    @RequestMapping(value=DoctorSvcApi.SEARCH_PATH_USERNAME, method=RequestMethod.GET)
    public @ResponseBody List<Doctor> findByUsername(@RequestParam(ServiceUtils.PARAMETER_USERNAME) String username) {
        return repository.findByUsername(username);
    }
}
