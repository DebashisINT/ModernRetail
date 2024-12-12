package com.example.modernretail.store

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.modernretail.database.StoreDao
import com.example.modernretail.database.StoreDtls
import com.example.modernretail.database.StoreEntity
import kotlinx.coroutines.flow.Flow

class StoreRepo(private val storeDao: StoreDao) {
    val pageSize : Int = 20

    fun getSearchStores(query:String): Flow<PagingData<StoreDtls>> {
        return Pager(config = PagingConfig(pageSize = pageSize, enablePlaceholders = false), pagingSourceFactory = {storeDao.getSearchPaging(query)}).flow
    }
}