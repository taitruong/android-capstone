package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Patient;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PatientSvcApi {

    public static final String PARAMETER_MEDICATION_ID = "medicationId";
    public static final String PARAMETER_MEDICAMENT_ID = "medicamentId";

	public static final String SVC_PATH = "/patient";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";
    public static final String SVC_PATH_PATIENT_MEDICATION =
            SVC_PATH +
                    "/{" + ServiceUtils.PARAMETER_ID + "}" +
                    "/medication/{" + PARAMETER_MEDICATION_ID + "}";
    public static final String SVC_PATH_PATIENT_MEDICAMENT =
            SVC_PATH +
                    "/{" + ServiceUtils.PARAMETER_ID + "}" +
                    "/medicament/{" + PARAMETER_MEDICAMENT_ID + "}";

	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";
    public static final String SEARCH_PATH_DOCTOR_USERNAME_AND_FILTER = SVC_PATH + "/search/findByDoctorUsernameAndFilter";

    @GET(SVC_PATH)
    public List<Patient> findAll();

    @POST(SVC_PATH)
    public Patient save(@Body Patient patient);

    @PUT(SVC_PATH_ID)
    public Patient update(@Path(ServiceUtils.PARAMETER_ID) long id, @Body Patient patient);

    @DELETE(SVC_PATH_ID)
    public Void delete(@Path(ServiceUtils.PARAMETER_ID) long id);

    @GET(SVC_PATH_ID)
    public Patient findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

    @DELETE(SVC_PATH_PATIENT_MEDICATION)
    public Patient deleteMedicationForPatient(
            @Path(ServiceUtils.PARAMETER_ID) long patientId,
            @Path(PARAMETER_MEDICATION_ID) long medicationId);

    @POST(SVC_PATH_PATIENT_MEDICAMENT)
    public Patient addMedicamentForPatient(
            @Path(ServiceUtils.PARAMETER_ID) long patientId,
            @Path(PARAMETER_MEDICAMENT_ID) long medicamentId);

    @GET(SEARCH_PATH_USERNAME)
    public Patient findByUsername(@Query(ServiceUtils.PARAMETER_USERNAME) String username);

    @GET(SEARCH_PATH_DOCTOR_USERNAME_AND_FILTER)
    public List<Patient> findByDoctorUsernameAndFilter(
            @Query(ServiceUtils.PARAMETER_USERNAME) String username,
            @Query(ServiceUtils.PARAMETER_FILTER) String filter);

}
