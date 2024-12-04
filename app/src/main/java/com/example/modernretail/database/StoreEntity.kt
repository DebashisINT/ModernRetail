package com.example.modernretail.database

import androidx.paging.PagingSource
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "TBL_STORE")
data class StoreEntity (
    @PrimaryKey
    @ColumnInfo(name = "store_id", defaultValue = "") var store_id:String="",
    @ColumnInfo(name = "store_name", defaultValue = "") var store_name:String="",
    @ColumnInfo(name = "store_address", defaultValue = "") var store_address:String="",
    @ColumnInfo(name = "store_contact_name", defaultValue = "") var store_contact_name:String="",
    @ColumnInfo(name = "store_contact_number", defaultValue = "") var store_contact_number:String="",
    @ColumnInfo(name = "store_contact_whatsapp", defaultValue = "") var store_contact_whatsapp:String="",
    @ColumnInfo(name = "store_contact_email", defaultValue = "") var store_contact_email:String="",
    @ColumnInfo(name = "store_type", defaultValue = "") var store_type:String="",
    @ColumnInfo(name = "store_size_area", defaultValue = "") var store_size_area:String="",
    @ColumnInfo(name = "store_pic_url", defaultValue = "") var store_pic_url:String="",
    @ColumnInfo(name = "isUploaded", defaultValue = "0") var isUploaded:Boolean=false
)

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj:StoreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objL: List<StoreEntity>)

    @Query("delete from TBL_STORE")
    suspend fun deleteAll()

    @Query("select * from TBL_STORE")
    suspend fun getAll() : List<StoreEntity>

    @Query("select * from TBL_STORE where store_name like '%' || :searchQuery || '%'")
    fun getSearchPaging(searchQuery:String) : PagingSource<Int, StoreEntity>
}