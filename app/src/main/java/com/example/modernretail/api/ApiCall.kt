package com.example.mineee.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

    @POST("ModernRetailInfoDetails/StoreInfoSave")
    suspend fun syncStore(@Body obj: StoreSync): BaseResponse

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
                .baseUrl("http://3.7.30.86:8075/")
                .build()
            return retrofit.create(ApiCall::class.java)
        }
    }

}