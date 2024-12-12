package com.example.mineee.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiCall {

    @FormUrlEncoded
    @POST("UserLogin/Login")
    suspend fun login(
        @Field("login_id") login_id: String, @Field("login_password") login_password: String,
        @Field("app_version") app_version: String, @Field("device_token") device_token: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("ModernRetailInfoDetails/StoreTypeLists")
    suspend fun getStoreType(@Field("user_id") user_id: String): StoreTypeResponse

    @FormUrlEncoded
    @POST("ModernRetailInfoDetails/PinCityStateLists")
    suspend fun getPinState(@Field("user_id") user_id: String): PinStateResponse

    @FormUrlEncoded
    @POST("ModernRetailInfoDetails/StoreInfoFetchLists")
    suspend fun getStore(@Field("user_id") user_id: String): StoreResponse

    @POST("ModernRetailInfoDetails/StoreInfoSave")
    suspend fun syncStore(@Body obj: StoreSync): BaseResponse

    @Multipart
    @POST("ModernRetailImageInfo/StoreImageSave")
    suspend fun syncStoreImage(@Part("data") data: RequestBody,@Part attachments: MultipartBody.Part): BaseResponse

    companion object {
        fun create(): ApiCall {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://3.7.30.86:8075/API/")
                .build()
            return retrofit.create(ApiCall::class.java)
        }

        fun createMultiPart(): ApiCall {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("http://3.7.30.86:8075/")
                .build()
            return retrofit.create(ApiCall::class.java)
        }
    }
}