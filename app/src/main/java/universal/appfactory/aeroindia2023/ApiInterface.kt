package universal.appfactory.aeroindia2023

import retrofit2.Call
import retrofit2.http.*
import universal.appfactory.aeroindia2023.Notifications.NotificationRequestModel
import universal.appfactory.aeroindia2023.Notifications.NotificationResponseModel
import universal.appfactory.aeroindia2023.agendas.AgendaResponse
import universal.appfactory.aeroindia2023.agendas.CategoryResponse
import universal.appfactory.aeroindia2023.agendas.LocationResponse
import universal.appfactory.aeroindia2023.agendas.TimeResponse
import universal.appfactory.aeroindia2023.delegate.DelegateResponse
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorResponse
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorResponse2
import universal.appfactory.aeroindia2023.faqs.FaqsResponse
import universal.appfactory.aeroindia2023.liaison_officer.LiaisonResponse
import universal.appfactory.aeroindia2023.liaison_officer.trail.trailhistory.TrailHistoryResponse
import universal.appfactory.aeroindia2023.liaison_officer.trail.trailhome.SaveTrailFeedbackModel
import universal.appfactory.aeroindia2023.liaison_officer.trail.trailhome.SaveTrailFeedbackResponse
import universal.appfactory.aeroindia2023.liaison_officer.trail.trailhome.TrailResponse
import universal.appfactory.aeroindia2023.products.ProductResponse
import universal.appfactory.aeroindia2023.products.ProductResponse2
import universal.appfactory.aeroindia2023.speakers.SpeakerResponse
import universal.appfactory.aeroindia2023.weather.WeatherResponse

interface ApiInterface {

    @POST("api/complaint-save")
    fun sendReq(@Body requestModel: RequestModel, @Header("Authorization") bearerToken: String) : Call<ResponseModel>

    @GET("api/get-speaker")
    fun getSpeakers(@Header("Authorization") bearerToken: String) : Call<SpeakerResponse?>?

    @POST("api/user")
    fun getExhibitors(@Header("Authorization") bearerToken: String) : Call<ExhibitorResponse?>?

    @GET("api/get-agenda")
    fun getAgendas(@Header("Authorization") bearerToken: String) : Call<AgendaResponse?>?

    @GET("api/get-products")
    fun getProducts(@Header("Authorization") bearerToken: String) : Call<ProductResponse?>?

    @Headers(
        "Referrer-Policy: origin"
    )
    @POST("api/register-user")
    fun sendUserData(@Body userDataRequestModel: UserDataRequestModel,@Header("Authorization") bearerToken: String): Call<UserRegisterResponseModel>

    @POST("api/register-verify")
    fun verifyUserData(@Body userVerifyRequestModel: UserVerifyRequestModel,@Header("Authorization") bearerToken: String): Call<UserVerifyResponseModel>

    @Headers(
        "Referrer-Policy: origin"
    )
    @POST("api/login-user")
    fun verifyUserLogin(@Body userLoginDataRequestModel: UserLoginDataRequestModel,@Header("Authorization") bearerToken: String): Call<UserLoginDataResponseModel>

    @POST("api/update-user-profile")
    fun updateUserInfo(@Body userInfoUpdateRequestModel: UserInfoUpdateRequestModel, @Header("Authorization") bearerToken: String): Call<UserInfoUpdateResponseModel>

    @POST("api/complaint-resolved-save")
    fun resolved(@Body resolvedRequestModel:ResolvedRequestModel, @Header("Authorization") bearerToken:String):Call<ResolvedResponseModel>

    @GET("api/get-complaint/{id}")
    fun getproblem(@Header("Authorization") bearerToken: String,@Path("id") id: Int) :Call<ZonalManagerResponse?>?

    @GET("api/get-complaint")
    fun getmanagers(@Header ("Authorization") bearerToken: String) :Call<ManagerResponse?>?

    @GET("api/get-user-complaint-history/{id}")
    fun gethistory(@Header("Authorization") bearerToken: String,@Path("id") id: Int) :Call<UserHistoryResponse?>?

    @GET("api/get-agenda/{id}")
    fun getAgendaSpeaker(@Header("Authorization") bearerToken: String, @Path("id") id: Int) : Call<SpeakerResponse?>?

    @GET("api/get-speaker/{id}")
    fun getSpeakerAgenda(@Header("Authorization") bearerToken: String, @Path("id") id: Int) : Call<AgendaResponse?>?

    @GET("api/get-agenda-classification/category")
    fun getAgendaClassificationCategory(@Header("Authorization") bearerToken: String) : Call<CategoryResponse?>?

    @GET("api/get-agenda-classification/time")
    fun getAgendaClassificationTime(@Header("Authorization") bearerToken: String) : Call<TimeResponse?>?

    @GET("api/get-agenda-classification/location")
    fun getAgendaClassificationLocation(@Header("Authorization") bearerToken: String) : Call<LocationResponse?>?

    @POST("api/get-exhibitor-product/{id}")
    fun getExhibitorProduct(@Header("Authorization") bearerToken: String, @Path("id") id: Int) : Call<ProductResponse2?>?

    @GET("api/get-exhibitor/{id}")
    fun getProductExhibitor(@Header("Authorization") bearerToken: String, @Path("id") id: String) : Call<ExhibitorResponse2?>?

    @GET("api/get-faq/{id}")
    fun getFaqs(@Header("Authorization") bearerToken: String, @Path("id") id: Int) : Call<FaqsResponse?>?

    @GET("api/get-delegate/{id}")
    fun getDelegates(@Header("Authorization") bearerToken: String, @Path("id") id: Int) : Call<DelegateResponse?>?

    @GET("api/get-liaison-officer/{id}")
    fun getLiaisonOfficers(@Header("Authorization") bearerToken: String, @Path("id") id: Int) : Call<LiaisonResponse?>?

    @GET("api/get-trail")
    fun getTrail(@Header("Authorization") bearerToken: String): Call<TrailResponse?>?

    @GET("api/get-trail-history/{id}/{type}")
    fun getTrailHistory(@Header("Authorization") bearerToken: String,@Path("id") id: Int,@Path("type") type: String) : Call<TrailHistoryResponse?>?

    @POST("api/save-trail-feedback")
    fun saveTrailFeedback(@Body saveTrailFeedbackModel: SaveTrailFeedbackModel, @Header("Authorization") bearerToken: String): Call<SaveTrailFeedbackResponse>

    @GET("forecast/daily?lat=12.9716&lon=77.5946&key=624f382318824a2ab0dedb2ee4c7fb22")
    fun getTemperature() : Call<WeatherResponse?>?

    @POST("notifications")
    fun sendNotification(@Body notificationRequestModel: NotificationRequestModel, @Header("Authorization") REST_API_KEY: String, @Header("Content-Type") contentType: String): Call<NotificationResponseModel>

}