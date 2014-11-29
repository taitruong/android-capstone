package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.LastNameComparator;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.MedicamentRepository;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Controller
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private MedicamentRepository medicamentRepository;

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
    public @ResponseBody Patient findByUsername(@RequestParam(ServiceUtils.PARAMETER_USERNAME) String username) {
        return repository.findByUsername(username);
    }

    @RequestMapping(value=PatientSvcApi.SEARCH_PATH_DOCTOR_USERNAME_AND_FILTER, method=RequestMethod.GET)
    public @ResponseBody List<Patient> findByDoctorUsernameAndFilter(
            @RequestParam(ServiceUtils.PARAMETER_USERNAME) String username,
            @RequestParam(ServiceUtils.PARAMETER_FILTER) String filter) {

        filter = filter.toLowerCase().trim();
        //@TODO this is not the best solution, fix this later
        List<Patient> result = new ArrayList<Patient>();
        for (Patient patient: repository.findAll()) {
            for (Doctor doctor: patient.getDoctors()) {
                if (doctor.getUsername().equals(username)) {
                    if (TextUtils.isEmpty(filter)
                            || patient.getFirstName().toLowerCase().contains(filter)
                            || patient.getLastName().toLowerCase().contains(filter)
                            || patient.getMedicalRecordNumber().toLowerCase().contains(filter)) {
                        result.add(patient);
                    }
                    break;
                }
            }
        }

        Collections.sort(result, new LastNameComparator<Patient>());
        return result;
    }

    @RequestMapping(value= PatientSvcApi.SVC_PATH_PATIENT_MEDICATION, method= RequestMethod.DELETE)
    public @ResponseBody Patient deleteMedicationForPatient(
            @PathVariable(ServiceUtils.PARAMETER_ID) long patientId,
            @PathVariable(PatientSvcApi.PARAMETER_MEDICATION_ID)long medicationId) {
        Patient patient = repository.findOne(patientId);
        List<Medication> medications = new ArrayList<Medication>();
        for (Medication medication: patient.getMedications()) {
            if (medication.getId() != medicationId) {
                medications.add(medication);
            }
        }
        patient.setMedications(medications);
        return repository.save(patient);
    }

    @RequestMapping(value= PatientSvcApi.SVC_PATH_PATIENT_MEDICAMENT, method= RequestMethod.POST)
    public @ResponseBody Patient addMedicamentForPatient(
            @PathVariable(ServiceUtils.PARAMETER_ID) long patientId,
            @PathVariable(PatientSvcApi.PARAMETER_MEDICAMENT_ID)long medicamentId) {
        Medicament medicament = medicamentRepository.findOne(medicamentId);
        if (medicament == null) {
            throw new IllegalArgumentException("Cannot add medication. Medicament with ID " + medicamentId + " not found!");
        }
        Patient patient = repository.findOne(patientId);
        Medication medication = new Medication();
        medication.setMedicament(medicament);
        patient.getMedications().add(medication);
        patient = repository.save(patient);
        return patient;
    }


    @RequestMapping(value= PatientSvcApi.SVC_PATH_PATIENT_CHECKIN, method= RequestMethod.POST)
    public @ResponseBody Patient addCheckIn(
            @PathVariable(ServiceUtils.PARAMETER_ID) long patientId,
            @RequestBody CheckIn checkIn) {
        Patient patient = repository.findOne(patientId);
        patient.getCheckIns().add(checkIn);
        patient = repository.save(patient);
        return patient;
    }

    protected void createAlarms(List<SymptomTime> checkInSymptomTimes, Patient patient) {
        if (checkInSymptomTimes.isEmpty()) {
            return;
        }
        for (SymptomTime checkInSymptomTime: checkInSymptomTimes) {
//            for (CheckIn checkIn: )
//            for (SymptomTime symptomTime: patient.get)
        }
    }

}