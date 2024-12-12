package com.example.mineee.api

import com.example.modernretail.database.PinStateEntity
import com.example.modernretail.database.StoreEntity
import com.example.modernretail.database.StoreTypeEntity
import okhttp3.MultipartBody
import retrofit2.http.Multipart


open class BaseResponse(var status:String="",var message:String="")

data class LoginResponse(var user_name:String="",var user_id:String="",var contact_number:String="",
    var email:String="",var city:String="",var state:String="",var country:String="",var pincode:String="",
    var address:String="",var profile_pic_url:String=""):BaseResponse()

data class StoreTypeResponse(var store_type_list:ArrayList<StoreTypeEntity>):BaseResponse()

data class StoreResponse(var user_id:String="",var store_list:ArrayList<StoreEntity>):BaseResponse()

data class PinStateResponse(var pin_state_list:ArrayList<PinStateEntity>):BaseResponse()

//store sync
data class StoreSync(var user_id:String="",var store_list:ArrayList<StoreEntity> = ArrayList()):BaseResponse()
data class StoreImageBody(var user_id:String="",var store_id:String="")
