package com.example.modernretail.api

import android.content.Context
import android.net.Uri
import android.os.FileUtils
import com.example.mineee.api.ApiCall
import com.example.mineee.api.BaseResponse
import com.example.mineee.api.StoreImageBody
import com.example.mineee.api.StoreSync
import com.example.modernretail.others.Pref
import com.example.xst.AppDatabase
import com.google.android.gms.common.api.Api
import com.google.gson.Gson
import kotlinx.coroutines.delay
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object SyncApi {
    suspend fun syncStoreApi(mContext: Context, storeID: String): BaseResponse {
        var obj = AppDatabase.getDatabase(mContext).storeDao.getUnsyncStoreByID(storeID)
        if (obj != null) {
            val syncObj = StoreSync()
            syncObj.user_id = Pref.user_id
            syncObj.store_list.add(obj)
            val response = ApiCall.create().syncStore(syncObj)
            return response
        } else {
            return BaseResponse("", "")
        }
    }

    suspend fun syncStoreImageApi(mContext: Context, storeID: String, storeImageUrl: String): BaseResponse {
        try {
            val storeData = StoreImageBody().apply {
                user_id = Pref.user_id
                store_id = storeID
            }
            val storeDataRequest = RequestBody.create("application/json".toMediaTypeOrNull(),Gson().toJson(storeData))
            val imageFile = File(storeImageUrl)
            if (!imageFile.exists()) {
                return BaseResponse("205","")
            }
            val imageBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
            val imageData = MultipartBody.Part.createFormData("attachments", imageFile.name, imageBody)

            val response = ApiCall.createMultiPart().syncStoreImage(storeDataRequest,imageData)
            return response
        } catch (e: Exception) {
            e.printStackTrace()
            return BaseResponse("205","")
        }
    }
}