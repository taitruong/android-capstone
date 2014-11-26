package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.repository.SymptomTimeRepository;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomTimeSvcApi;
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
public class SymptomTimeController {

    @Autowired
    private SymptomTimeRepository repository;

    @RequestMapping(value= SymptomTimeSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<SymptomTime> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= SymptomTimeSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    SymptomTime findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value=SymptomTimeSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody SymptomTime save(@RequestBody SymptomTime model) {
        return repository.save(model);
    }

}
