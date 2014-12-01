package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.AlarmRepository;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.DoctorRepository;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientAlarmDTO;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class AlarmController {

    @Autowired
    private PatientRepository patientRepository;

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

    @RequestMapping(value= AlarmSvcApi.SEARCH_PATH_PATIENT_ID, method=RequestMethod.GET)
    public @ResponseBody
    List<Alarm> findByPatientId(@RequestParam(ServiceUtils.PARAMETER_ID) long patientId) {
        return repository.findByPatientId(patientId);
    }

    @RequestMapping(value= AlarmSvcApi.SEARCH_PATH_DOCTOR_USERNAME, method=RequestMethod.GET)
    public @ResponseBody List<PatientAlarmDTO> getPatientAlarmsByDoctorUserName(
            @RequestParam(ServiceUtils.PARAMETER_USERNAME) String doctorUserName) {
        List<PatientAlarmDTO> patientAlarms = new ArrayList<PatientAlarmDTO>();

        List<Patient> patients = Lists.newArrayList(patientRepository.findAll());
        for (Patient patient: patients) {
            for (Doctor doctor: patient.getDoctors()) {
                if (doctor.getUsername().equals(doctorUserName)) {
                    List<Alarm> alarms = findByPatientId(patient.getId());
                    int alarmsSize = alarms.size();
                    if (alarmsSize > 0) {
                        //get latest alarm
                        PatientAlarmDTO patientAlarmDTO = new PatientAlarmDTO();
                        patientAlarmDTO.alarm = alarms.get(alarmsSize - 1);
                        patientAlarmDTO.patient = patient;
                        patientAlarms.add(patientAlarmDTO);
                    }
                    break;
                }
            }
        }

        return patientAlarms;
    }

}