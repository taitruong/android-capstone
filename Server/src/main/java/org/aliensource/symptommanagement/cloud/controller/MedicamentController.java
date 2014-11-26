package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.CheckInRepository;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.MedicamentRepository;
import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicamentSvcApi;
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
public class MedicamentController {

    @Autowired
    private MedicamentRepository repository;

    @RequestMapping(value= MedicamentSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Medicament> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= MedicamentSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Medicament findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value=MedicamentSvcApi.SVC_PATH, method=RequestMethod.POST)
    public @ResponseBody Medicament save(@RequestBody Medicament model) {
        return repository.save(model);
    }
}
