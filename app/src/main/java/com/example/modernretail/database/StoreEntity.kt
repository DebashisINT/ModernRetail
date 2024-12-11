package com.example.modernretail.database

import androidx.paging.PagingSource
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "MR_STORE")
data class StoreEntity (
    @PrimaryKey
    @ColumnInfo(name = "store_id", defaultValue = "") var store_id:String="",
    @ColumnInfo(name = "store_name", defaultValue = "") var store_name:String="",
    @ColumnInfo(name = "store_address", defaultValue = "") var store_address:String="",
    @ColumnInfo(name = "store_pincode", defaultValue = "") var store_pincode:String="",
    @ColumnInfo(name = "store_lat", defaultValue = "") var store_lat:String="",
    @ColumnInfo(name = "store_long", defaultValue = "") var store_long:String="",
    @ColumnInfo(name = "store_contact_name", defaultValue = "") var store_contact_name:String="",
    @ColumnInfo(name = "store_contact_number", defaultValue = "") var store_contact_number:String="",
    @ColumnInfo(name = "store_contact_whatsapp", defaultValue = "") var store_contact_whatsapp:String="",
    @ColumnInfo(name = "store_email", defaultValue = "") var store_email:String="",
    @ColumnInfo(name = "store_type", defaultValue = "") var store_type:String="",
    @ColumnInfo(name = "store_size_area", defaultValue = "") var store_size_area:String="",
    @ColumnInfo(name = "store_state_id", defaultValue = "") var store_state_id:String="",
    @ColumnInfo(name = "remarks", defaultValue = "") var remarks:String="",
    @ColumnInfo(name = "create_date_time", defaultValue = "") var create_date_time:String="",
    @ColumnInfo(name = "store_pic_url", defaultValue = "") var store_pic_url:String="",
    @ColumnInfo(name = "isUploaded", defaultValue = "0") var isUploaded:Boolean=false
)

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj:StoreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objL: List<StoreEntity>)

    @Query("delete from MR_STORE")
    suspend fun deleteAll()

    @Query("select * from MR_STORE")
    suspend fun getAll() : List<StoreEntity>

   /* @Query("select * from MR_STORE where store_name like '%' || :searchQuery || '%'")
    fun getSearchPaging(searchQuery:String) : PagingSource<Int, StoreEntity>*/

    @Query("select * from MR_STORE where store_name like '%' || :searchQuery || '%'")
    fun getSearchPaging(searchQuery:String) : PagingSource<Int, StoreEntity>
}

data class StoreDtls(var store_id:String="",var store_name:String="",var store_address:String="",var store_pincode:String="",var store_lat:String="",
                     var store_long:String="",var store_contact_name:String="",var store_contact_number:String="",var store_contact_whatsapp:String="",
                     var store_email:String="", var store_type:String="",var type_name:String="",var store_size_area:String="",
                     var store_state_id:String="",var state_name:String="",var remarks:String="",var create_date_time:String="",var store_pic_url:String="",
    var isUploaded:Boolean = false)