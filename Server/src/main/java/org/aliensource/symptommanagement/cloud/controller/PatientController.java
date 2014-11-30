package org.aliensource.symptommanagement.cloud.controller;

import com.google.common.collect.Lists;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.AlarmRepository;
import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.CheckInRepository;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.IntakeTimeRepository;
import org.aliensource.symptommanagement.cloud.repository.LastNameComparator;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.MedicamentRepository;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientRepository;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomRepository;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.repository.SymptomTimeRepository;
import org.aliensource.symptommanagement.cloud.repository.TimestampComparator;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Created by ttruong on 26-Nov-14.
 */
@Service
@Controller
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private SymptomTimeRepository symptomTimeRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Autowired
    private IntakeTimeRepository intakeTimeRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @RequestMapping(value=PatientSvcApi.SVC_PATH, method=RequestMethod.GET)
    public @ResponseBody List<Patient> findAll(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value= PatientSvcApi.SVC_PATH_ID, method= RequestMethod.GET)
    public @ResponseBody
    Patient findOne(@PathVariable(ServiceUtils.PARAMETER_ID) long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value= PatientSvcApi.SEARCH_PATH_SYMPTOM_TIMES_FOR_PATIENT, method= RequestMethod.GET)
    public @ResponseBody List<SymptomTime>[] getSymptomTimesByEatDrinkSoreThroat(@PathVariable(ServiceUtils.PARAMETER_ID) long patientId) {
        Patient patient = findOne(patientId);
        return getSymptomTimesByEatDrinkSoreThroat(patient, true, true);
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
            @RequestBody CheckIn detachedCheckIn) {
        CheckIn checkIn = checkInRepository.save(new CheckIn());
        checkIn.setPatientId(patientId);
        checkIn.setTimestamp(detachedCheckIn.getTimestamp());

        List<SymptomTime> persistedSymptomTimes = new ArrayList<SymptomTime>();
        for (SymptomTime symptomTime: detachedCheckIn.getSymptomTimes()) {
            //first attach symptom from DB instead of using detached symptom provided by client
            Symptom symptom = symptomRepository.findOne(symptomTime.getSymptom().getId());
            symptomTime.setSymptom(symptom);
//            persistedSymptomTimes.add(symptomTimeRepository.save(symptomTime));
        }
        List<IntakeTime> persistedIntakeTimes = new ArrayList<IntakeTime>();
        for (IntakeTime intakeTime: detachedCheckIn.getIntakeTimes()) {
            //first attach medicament from DB instead of using detached medicament provided by client
            Medicament medicament = medicamentRepository.findOne(intakeTime.getMedicament().getId());
            intakeTime.setMedicament(medicament);
//            persistedIntakeTimes.add(intakeTimeRepository.save(intakeTime));
        }

//        checkIn.setSymptomTimes(persistedSymptomTimes);
//        checkIn.setIntakeTimes(persistedIntakeTimes);
        checkIn.setSymptomTimes(detachedCheckIn.getSymptomTimes());
        checkIn.setIntakeTimes(detachedCheckIn.getIntakeTimes());
        checkIn = checkInRepository.save(checkIn);

        Patient patient = repository.findOne(patientId);
        createAlarms(patient, checkIn.getSymptomTimes());
        return patient;
    }

    protected void createAlarms(Patient patient, List<SymptomTime> checkInSymptomTimes) {
        if (checkInSymptomTimes.isEmpty()) {
            return;
        }
        boolean addSoreThroat = false;
        boolean addEatDrink = false;
        for (SymptomTime checkInSymptomTime: checkInSymptomTimes) {
            if (ServiceUtils.SYMPTOM_TYPE_SORE_THROAT.equals(checkInSymptomTime.getSymptom().getType())) {
                addSoreThroat = true;
            } else {
                addEatDrink = true;
            }
        }
        //filter all symptom times with the same symptom
        List<SymptomTime>[] result = getSymptomTimesByEatDrinkSoreThroat(patient, addSoreThroat, addEatDrink);
        List<SymptomTime> allSoreThroatSymptomTimes = result[0];
        List<SymptomTime> allEatDrinkSymptomTimes = result[1];

        // TODO sort by time, it is rare but possible that the id starts from scratch again
        createSoreThroatAlarm(patient, allSoreThroatSymptomTimes);
        createEatDrinkAlarm(patient, allEatDrinkSymptomTimes);
    }

    protected List<SymptomTime>[] getSymptomTimesByEatDrinkSoreThroat(
            Patient patient, boolean addSoreThroat, boolean addEatDrink) {
        List<SymptomTime> allSoreThroatSymptomTimes = new ArrayList<SymptomTime>();
        List<SymptomTime> allEatDrinkSymptomTimes = new ArrayList<SymptomTime>();
        List<CheckIn> checkIns = checkInRepository.findByPatientId(patient.getId());
        for (CheckIn checkIn: checkIns) {
            for (SymptomTime symptomTime: checkIn.getSymptomTimes()) {
                if (ServiceUtils.SYMPTOM_TYPE_SORE_THROAT.equals(symptomTime.getSymptom().getType())) {
                    if (addSoreThroat) {
                        allSoreThroatSymptomTimes.add(symptomTime);
                    }
                } else {
                    if (addEatDrink) {
                        allEatDrinkSymptomTimes.add(symptomTime);
                    }
                }
            }
        }
        List<SymptomTime>[] result = new List[2];
        allSoreThroatSymptomTimes.sort(new TimestampComparator<SymptomTime>());
        allEatDrinkSymptomTimes.sort(new TimestampComparator<SymptomTime>());
        result[0] = allSoreThroatSymptomTimes;
        result[1] = allEatDrinkSymptomTimes;
        return result;
    }

    protected void createSoreThroatAlarm(Patient patient, List<SymptomTime> allSymptomTimes) {
        int size = allSymptomTimes.size();
        if (size >= 2) {
            //the symptom that has been created
            SymptomTime latestSymptom = allSymptomTimes.get(size - 1);
            Calendar latestCal = new GregorianCalendar();
            latestCal.setLenient(true);
            latestCal.setTimeInMillis(latestSymptom.getTimestamp());

            SymptomTime previousSymptom = allSymptomTimes.get(size -2);
            Calendar previousCal = new GregorianCalendar();
            previousCal.setLenient(true);
            previousCal.setTimeInMillis(previousSymptom.getTimestamp());

            // alarms are created only for severity > 0
            if (previousSymptom.getSeverity() > 0 && latestSymptom.getSeverity() > 0) {
                previousCal.add(Calendar.HOUR_OF_DAY, 12);
                //must be at least 12 hours difference
                if (previousCal.compareTo(latestCal) <= 0) {
                    //12 hours of severe pain?
                    boolean create = false;
                    if (latestSymptom.getSeverity() == 2) {
                        create = true;
                        System.out.println(">>>>> 12 hours of severe Sore Throat");
                    } else {
                        //add another 4 hours, 16 hours of difference
                        previousCal.add(Calendar.HOUR_OF_DAY, 4);
                        if (previousCal.compareTo(latestCal) <= 0
                                && latestSymptom.getSeverity() == 1) {
                            create = true;
                            System.out.println(">>>>> 16 hours of moderate/severe Sore Throat");
                        }
                    }
                    if (create) {
                        Alarm alarm = new Alarm();
                        alarm.setStart(previousSymptom.getTimestamp());
                        alarm.setEnd(latestSymptom.getTimestamp());
                        alarm.setSymptom(latestSymptom.getSymptom());
                        alarm.setPatientId(patient.getId());
                        alarmRepository.save(alarm);
                    }
                }

            }
        }
    }

    protected void createEatDrinkAlarm(Patient patient, List<SymptomTime> allSymptomTimes) {
        int size = allSymptomTimes.size();
        if (size >= 2) {
            //the symptom that has been created
            SymptomTime latestSymptom = allSymptomTimes.get(size - 1);
            Calendar latestCal = new GregorianCalendar();
            latestCal.setLenient(true);
            latestCal.setTimeInMillis(latestSymptom.getTimestamp());

            SymptomTime previousSymptom = allSymptomTimes.get(size -2);
            Calendar previousCal = new GregorianCalendar();
            previousCal.setLenient(true);
            previousCal.setTimeInMillis(previousSymptom.getTimestamp());

            // alarms are created only for severity > 0
            if (previousSymptom.getSeverity() == 2 && latestSymptom.getSeverity() == 2) {
                previousCal.add(Calendar.HOUR_OF_DAY, 12);
                //must be at least 12 hours difference
                if (previousCal.compareTo(latestCal) <= 0) {
                    Alarm alarm = new Alarm();
                    alarm.setStart(previousSymptom.getTimestamp());
                    alarm.setEnd(latestSymptom.getTimestamp());
                    alarm.setSymptom(latestSymptom.getSymptom());
                    alarm.setPatientId(patient.getId());
                    alarmRepository.save(alarm);
                }
            }
        }
    }

    @PostConstruct
    protected void createSampleCheckIns() {
        System.out.println(">>>> init some some data");
        createEatDrinkCheckIn();
        createSoreThroatCheckIn();
    }

    protected void createEatDrinkCheckIn() {
        Patient patient = repository.findOne(1L);

        Calendar base = new GregorianCalendar();
        //create checkin with sore throat
        base.add(Calendar.DAY_OF_MONTH, -1);
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        SymptomTime symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        Symptom eatDrink = symptomRepository.findByType(ServiceUtils.SYMPTOM_TYPE_EAT_DRINK).get(0);
        symptomTime.setSymptom(eatDrink);
        symptomTime.setSeverity(0);
        //save
        addCheckIn(1, checkIn);

        //create checkin with sore throat
        base.add(Calendar.HOUR_OF_DAY, 2);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        eatDrink = symptomRepository.findByType(ServiceUtils.SYMPTOM_TYPE_EAT_DRINK).get(0);
        symptomTime.setSymptom(eatDrink);
        symptomTime.setSeverity(2);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(eatDrink);
        symptomTime.setSeverity(2);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 4);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(eatDrink);
        symptomTime.setSeverity(0);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 2);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(eatDrink);
        symptomTime.setSeverity(1);
        //save
        addCheckIn(1, checkIn);

    }

    protected void createSoreThroatCheckIn() {
        Patient patient = repository.findOne(1L);
        Symptom soreThroat = symptomRepository.findByType(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT).get(0);

        Calendar base = new GregorianCalendar();
        //create checkin with sore throat
        base.add(Calendar.DAY_OF_MONTH, -2);
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        SymptomTime symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(0);
        //save
        addCheckIn(1, checkIn);

        //create checkin with sore throat
        base.add(Calendar.HOUR_OF_DAY, 2);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(0);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 8);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(1);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 16);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(1);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 4);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(2);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(2);
        //save
        addCheckIn(1, checkIn);

        base.add(Calendar.HOUR_OF_DAY, 3);
        checkIn = new CheckIn();
        checkIn.setTimestamp(base.getTimeInMillis());
        symptomTimes = new ArrayList<SymptomTime>();
        symptomTime = new SymptomTime();
        symptomTimes.add(symptomTime);
        checkIn.setSymptomTimes(symptomTimes);
        //set values
        symptomTime.setTimestamp(base.getTimeInMillis());
        symptomTime.setSymptom(soreThroat);
        symptomTime.setSeverity(0);
        //save
        addCheckIn(1, checkIn);
    }
}