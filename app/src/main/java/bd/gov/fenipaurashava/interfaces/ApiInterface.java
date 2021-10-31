package bd.gov.fenipaurashava.interfaces;

import bd.gov.fenipaurashava.modelAdminLoginPOST.LoginResponse;
import bd.gov.fenipaurashava.modelCitizenServiceCount.CitizenServiceGet;
import bd.gov.fenipaurashava.modelComplainDeletePOST.ComplainDeleteResponse;
import bd.gov.fenipaurashava.modelComplainSubjectUpdatePOST.ComplainSubjectUpdateResponse;
import bd.gov.fenipaurashava.modelComplainsubjectDeletePOST.ComplainSubjectDeleteResponse;
import bd.gov.fenipaurashava.modelEmployeeDeletePOST.EmployeeDeleteResponse;
import bd.gov.fenipaurashava.modelEmployeeUpdatePOST.EmployeeUpdateResponse;
import bd.gov.fenipaurashava.modelForAppoinmentReferringPOST.AppointmentReferringResponse;
import bd.gov.fenipaurashava.modelForAppoinmentSubjectSavePOST.AppointmentSubjectSaveResponse;
import bd.gov.fenipaurashava.modelForAppointmentDeletePOST.AppointmentDeleteResponse;
import bd.gov.fenipaurashava.modelForAppointmentFetchGET.AppointmentFeatchResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectDeletePOST.AppointmentSubjectDeleteResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectFetchAllGET.AppointmentSubjectFetchAllResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectGET.AppointmentSubjecResponse;
import bd.gov.fenipaurashava.modelForAppointmentSubjectUpdatePOST.AppointmentSubjectUpdateResponse;
import bd.gov.fenipaurashava.modelForComplainFechAllGET.ComplainResponse;
import bd.gov.fenipaurashava.modelForComplainReferringPOST.ComplainReferringResponse;
import bd.gov.fenipaurashava.modelForComplainSavePOST.ComplainSaveResponse;
import bd.gov.fenipaurashava.modelForComplainSubjectFetchGET.ComplainSubjectFetchResponse;
import bd.gov.fenipaurashava.modelForComplainSubjectSavePOST.ComplainSubjectSaveResponse;
import bd.gov.fenipaurashava.modelForDCAppointmentSavePOST.AppointmentSaveResponse;
import bd.gov.fenipaurashava.modelForDCMessageGET.DCMessageResponse;
import bd.gov.fenipaurashava.modelForEmployeeGET.EmployeeResponse;
import bd.gov.fenipaurashava.modelForEmployeeSavePOST.EmployeeSaveResponse;
import bd.gov.fenipaurashava.modelForInformationDeletePOST.InformationDeleteResponse;
import bd.gov.fenipaurashava.modelForInformationReferringPOST.InformationReferringResponse;
import bd.gov.fenipaurashava.modelForSMSSendPOST.SMSSendResponse;
import bd.gov.fenipaurashava.modelForSetInformationFetchGET.InformationGetResponse;
import bd.gov.fenipaurashava.modelForSetInformationPOST.SetInformationSaveResponse;
import bd.gov.fenipaurashava.modelForWorkScheduleDeletePOST.WorkScheduleDeleteResponse;
import bd.gov.fenipaurashava.modelForWorkSchedulePOST.WorkScheduleSaveResponse;
import bd.gov.fenipaurashava.modelForWorkScheduleUpdatePOST.WorkScheduleUpdateResponse;
import bd.gov.fenipaurashava.modelForWrokScheduleFetchGET.WorkScheduleFetchResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("employee")
    Call<EmployeeResponse> getEmployeeResponse(@Header("app-key") String appKey);

    @GET("admin/api/certificate/summary")
    Call<CitizenServiceGet> getServiceCount();




    @GET("dc/message")
    Call<DCMessageResponse> getDCMessageResponse(@Header("app-key") String appKey);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> setUserInfoForLogin(@Header("app-key") String appKey,
                                            @Field("username") String usename,
                                            @Field("password") String password);

    //employee/designation/2
    @GET("employee/designation/{id}")
    Call<EmployeeResponse> getDepartmentWiseEmployeeResponse(
            @Header("app-key") String appKey,
            @Path("id") String id);

    @FormUrlEncoded
    @POST("appointment/subject/save")
    Call<AppointmentSubjectSaveResponse> setAppointmentSubjectSaveResponse(
            @Header("app-key") String appKey,
            @Field("user_id") String user_id,
            @Field("name") String name);


    @GET("appointment/subject")
    Call<AppointmentSubjectFetchAllResponse> getAppointmentSubjectFetchResponse(@Header("app-key") String appKey);

    @FormUrlEncoded
    @POST("appointment/subject/delete/{id}")
    Call<AppointmentSubjectDeleteResponse> setAppointmentSubjectDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);

    //work/schedule/delete/1
    @FormUrlEncoded
    @POST("work/schedule/delete/{id}")
    Call<WorkScheduleDeleteResponse> setWorkScheduleDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);

    //complain/subject/save
    @FormUrlEncoded
    @POST("complain/subject/save")
    Call<ComplainSubjectSaveResponse> setComplainSubjectSaveResponse(
            @Header("app-key") String appKey,
            @Field("user_id") String user_id,
            @Field("name") String name);

    @GET("complain/subject")
    Call<ComplainSubjectFetchResponse> getComplainSubjectFetchResponse(@Header("app-key") String appKey);

    @FormUrlEncoded
    @POST("complain/subject/delete/{id}")
    Call<ComplainSubjectDeleteResponse> setComplainSubjectDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("complain/delete/{id}")
    Call<ComplainDeleteResponse> setComplainDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("complain/subject/update/{id}")
    Call<ComplainSubjectUpdateResponse> setComplainSubjectUpdateResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id,
            @Field("name") String name);

    @FormUrlEncoded
    @POST("information/delete/{id}")
    Call<InformationDeleteResponse> setInformationDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);

    //appointment/delete/1
    @FormUrlEncoded
    @POST("appointment/delete/{id}")
    Call<AppointmentDeleteResponse> appointmentDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);

    //appointment/delete/1
    @FormUrlEncoded
    @POST("employee/delete/{id}")
    Call<EmployeeDeleteResponse> employeeDeleteResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("appointment/subject/update/{id}")
    Call<AppointmentSubjectUpdateResponse> setAppointmentSubjectUpdateResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("user_id") String user_id,
            @Field("name") String name);

    @FormUrlEncoded
    @POST("work/schedule/update/{id}")
    Call<WorkScheduleUpdateResponse> setWorkScheduleUpdateResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Field("employee_id") String employee_id,
            @Field("user_id") String user_id,
            @Field("subject") String subject,
            @Field("schedule_date") String schedule_date,
            @Field("place") String place,
            @Field("details") String details);


    @GET("appointment/subject")
    Call<AppointmentSubjecResponse> getComplainOfDivisions(@Header("app-key") String appKey);


    @FormUrlEncoded
    @POST("appointment/save")
    Call<AppointmentSaveResponse> setAppointmentSave(@Header("app-key") String appKey,
                                                     @Field("employee_id") int employee_id,
                                                     @Field("subject_id") int subject_id,
                                                     @Field("name") String name,
                                                     @Field("mobile_no") String mobile_no,
                                                     @Field("appointment_date") String appointment_date,
                                                     @Field("reference") String reference,
                                                     @Field("address") String address,
                                                     @Field("description") String description);

    @FormUrlEncoded
    @POST("complain/save")
    Call<ComplainSaveResponse> setComplain(@Header("app-key") String appKey,
                                           @Field("employee_id") String employee_id,
                                           @Field("subject_id") int subject_id,
                                           @Field("complainant_name") String complainant_name,
                                           @Field("defendant_name") String defendant_name,
                                           @Field("mobile_no") String mobile_no,
                                           @Field("complain") String complain,
                                           @Field("complainant_address") String complainant_address,
                                           @Field("defendant_address") String defendant_address,
                                           @Field("picture") String picture);

    //complain/?user_id=12
    @GET("complain/")
    Call<ComplainResponse> getComplainResponse(@Header("app-key") String appKey,

                                               @Query("employee_id") int employee_id,
                                               @Query("user_id") int user_id);

    @FormUrlEncoded
    @POST("work/schedule/save")
    Call<WorkScheduleSaveResponse> setWorkScheduleSaveResponse(
            @Header("app-key") String appKey,

            @Field("user_id") String user_id,
            @Field("employee_id") String employee_id,
            @Field("subject") String subject,
            @Field("schedule_date") String user_schedule_dateid,
            @Field("place") String place,
            @Field("details") String details);

    //schedule?employee_id=0&date=2020-09-24
    //information?employee_id=1&user_id=1
    @GET("work/schedule/")
    Call<WorkScheduleFetchResponse> getWorkScheduleFetchResponse(@Header("app-key") String appKey,
                                                                 @Query("employee_id") int employee_id,
                                                                 @Query("from_date") String from_date,
                                                                 @Query("to_date") String to_date);

    @FormUrlEncoded
    @POST("sms")
    Call<SMSSendResponse> setSMSSendResponse(
            @Header("app-key") String appKey,
            @Field("user_id") int user_id,
            @Field("mobile_no") String mobile_no,
            @Field("message") String message);

    @FormUrlEncoded
    @POST("complain/refer")
    Call<ComplainReferringResponse> setComplainReferringResponse(
            @Header("app-key") String appKey,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("employee_id") int employee_id);

    @FormUrlEncoded
    @POST("information/refer")
    Call<InformationReferringResponse> setInformationReferringResponse(
            @Header("app-key") String appKey,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("employee_id") int employee_id);

    @FormUrlEncoded
    @POST("appointment/refer")
    Call<AppointmentReferringResponse> setAppointmentReferringResponse(
            @Header("app-key") String appKey,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("employee_id") int employee_id);

    @Multipart
    @POST("information/save")
    Call<SetInformationSaveResponse> setInformationSaveResponse(
            @Header("app-key") String appKey,
            @Part("name") RequestBody name,
            @Part("subject") RequestBody subject,
            @Part("address") RequestBody address,
            @Part("mobile_no") RequestBody mobile_no,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part picture);

    @Multipart
    @POST("employee/save")
    Call<EmployeeSaveResponse> employeeSaveResponse(
            @Header("app-key") String appKey,
            @Part("user_id") RequestBody user_id,
            @Part("name") RequestBody name,
            @Part("employee_id") RequestBody employee_id,
            @Part("designation") RequestBody designation,
            @Part("mobile_no") RequestBody mobile_no,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part picture,
            @Part("bcs_batch") RequestBody bcs_batch,
            @Part("joining_date") RequestBody joining_date,
            @Part("fb_id") RequestBody fb_id,
            @Part("tweeter_id") RequestBody tweeter_id,
            @Part("order_no") RequestBody order_no,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password);

    @Multipart
    @POST("employee/update/{id}")
    Call<EmployeeUpdateResponse> setEmployeeUpdateUpdateResponse(
            @Header("app-key") String appKey,
            @Path("id") int id,
            @Part("user_id") RequestBody user_id,
            @Part("name") RequestBody name,
            @Part("employee_id") RequestBody employee_id,
            @Part("designation") RequestBody designation,
            @Part("mobile_no") RequestBody mobile_no,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part picture,
            @Part("bcs_batch") RequestBody bcs_batch,
            @Part("joining_date") RequestBody joining_date,
            @Part("fb_id") RequestBody fb_id,
            @Part("tweeter_id") RequestBody tweeter_id,
            @Part("order_no") RequestBody order_no,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password);

    //information?employee_id=1&user_id=1
    @GET("information/")
    Call<InformationGetResponse> getInformationGetResponse(@Header("app-key") String appKey,
                                                           @Query("user_id") int user_id,
                                                           @Query("employee_id") int employee_i);

    //appointment/?employee_id&user_id=1
    @GET("appointment/")
    Call<AppointmentFeatchResponse> getAppointment(@Header("app-key") String appKey,
                                                   @Query("employee_id") int employee_id,
                                                   @Query("user_id") int user_id);


}
