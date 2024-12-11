package com.example.modernretail.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.modernretail.others.DropdownData

@Entity(tableName = "MR_STORE_TYPE")
data class StoreTypeEntity(
    @PrimaryKey
    @ColumnInfo(name = "type_id", defaultValue = "") var type_id:String="",
    @ColumnInfo(name = "type_name", defaultValue = "") var type_name:String=""
)

@Dao
interface StoreTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj:StoreTypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objL: List<StoreTypeEntity>)

    @Query("delete from MR_STORE_TYPE")
    suspend fun deleteAll()

    @Query("select * from MR_STORE_TYPE")
    suspend fun getAll() : List<StoreTypeEntity>

    @Query("select type_id as id,type_name as name from MR_STORE_TYPE")
    suspend fun getAllDropdown() : List<DropdownData>
}