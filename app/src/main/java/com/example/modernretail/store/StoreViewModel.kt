package com.example.modernretail.store

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.modernretail.database.StoreDtls
import com.example.modernretail.database.StoreEntity
import com.example.xst.AppDatabase

class StoreViewModel(application: Application) : AndroidViewModel(application) {
    private val query = MutableLiveData("")
    private val storeRepo: StoreRepo = StoreRepo(AppDatabase.getDatabase(application).storeDao)

    val storeSearch : LiveData<PagingData<StoreDtls>> = query.switchMap { searchQuery ->
        storeRepo.getSearchStores(searchQuery).asLiveData().cachedIn(viewModelScope)
    }
    fun setSearch(query: String){
        this.query.value = query
    }
}