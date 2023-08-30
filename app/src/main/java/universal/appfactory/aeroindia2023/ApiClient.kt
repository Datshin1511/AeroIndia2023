@file:Suppress("MemberVisibilityCanBePrivate")

package universal.appfactory.aeroindia2023

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val baseUrl = "http://aeroindia.gov.in/"
    private const val weatherUrl = "http://api.weatherbit.io/v2.0/"

    var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS) // was 60 seconds
        .readTimeout(60, TimeUnit.SECONDS) // was 60 seconds
        .writeTimeout(60, TimeUnit.SECONDS) // was 60 seconds
        .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            // we need to add converter factory to
            // convert JSON object to Java object

    }

    fun getInstance2(): Retrofit {
        return Retrofit.Builder().baseUrl(weatherUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        // we need to add converter factory to
        // convert JSON object to Java object

    }

}