package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.AlarmRepository;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
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
public class AlarmController {

    @Autowired
    private AlarmRepository repository;

    @RequestMapping(value=AlarmSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Alarm> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= AlarmSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Alarm findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value=AlarmSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Alarm save(@RequestBody Alarm model) {
        return repository.save(model);
    }

}
