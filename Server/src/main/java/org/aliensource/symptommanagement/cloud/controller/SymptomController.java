package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomRepository;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
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

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class SymptomController {

    @Autowired
    private SymptomRepository repository;

    @RequestMapping(value= SymptomSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Symptom> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= SymptomSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Symptom findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value= SymptomSvcApi.SEARCH_PATH_TYPE, method= RequestMethod.GET)
    public @ResponseBody
    List<Symptom> findByType(@RequestParam(ServiceUtils.PARAMETER_TYPE) String type) {
        return repository.findByType(type);
    }

    @RequestMapping(value=SymptomSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Symptom save(@RequestBody Symptom model) {
        return repository.save(model);
    }

}
