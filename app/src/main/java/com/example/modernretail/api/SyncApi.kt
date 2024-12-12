package com.example.modernretail.api

import android.content.Context
import com.example.mineee.api.ApiCall
import com.example.mineee.api.BaseResponse
import com.example.mineee.api.StoreSync
import com.example.modernretail.others.Pref
import com.example.xst.AppDatabase

object SyncApi {
    suspend fun syncStoreApi(mContext: Context, storeID:String): BaseResponse {
        var obj = AppDatabase.getDatabase(mContext).storeDao.getUnsyncStoreByID(storeID)
        if(obj != null){
            val syncObj = StoreSync()
            syncObj.user_id = Pref.user_id
            syncObj.store_list.add(obj)
            val response = ApiCall.create().syncStore(syncObj)
            return response
        }else{
            return BaseResponse("","")
        }
    }
}