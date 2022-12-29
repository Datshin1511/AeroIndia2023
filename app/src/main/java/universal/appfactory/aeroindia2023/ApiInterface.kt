package universal.appfactory.aeroindia2023
import retrofit2.Call
import retrofit2.http.*
import universal.appfactory.aeroindia2023.agendas.AgendaResponse
import universal.appfactory.aeroindia2023.agendas.CategoryResponse
import universal.appfactory.aeroindia2023.agendas.LocationResponse
import universal.appfactory.aeroindia2023.agendas.TimeResponse
import universal.appfactory.aeroindia2023.exhibitors.ExhibitorResponse
import universal.appfactory.aeroindia2023.products.ProductResponse
import universal.appfactory.aeroindia2023.speakers.SpeakerResponse

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

    @POST("api/register-user")
    fun sendUserData(@Body userDataRequestModel: UserDataRequestModel,@Header("Authorization") bearerToken: String): Call<UserRegisterResponseModel>

    @POST("api/register-verify")
    fun verifyUserData(@Body userVerifyRequestModel: UserVerifyRequestModel,@Header("Authorization") bearerToken: String): Call<UserVerifyResponseModel>

    @POST("api/login-user")
    fun verifyUserLogin(@Body userLoginDataRequestModel: UserLoginDataRequestModel,@Header("Authorization") bearerToken: String): Call<UserLoginDataResponseModel>

    @GET("api/get-complaint/1")
    fun getproblem(@Header("Authorization") bearerToken: String) :Call<ZonalManagerResponse?>?

    @GET("api/get-complaint")
    fun getmanagers(@Header ("Authorization") bearerToken: String) :Call<ManagerResponse?>?

    @GET("api/get-users")
    fun gethistory(@Header("Authorization") bearerToken: String) :Call<UserHistoryResponse?>?

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

}