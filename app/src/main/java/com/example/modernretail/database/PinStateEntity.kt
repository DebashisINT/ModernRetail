package com.example.modernretail.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "MR_PIN_STATE")
data class PinStateEntity(
    @PrimaryKey
    @ColumnInfo(name = "pin_id", defaultValue = "") var pin_id:String="",
    @ColumnInfo(name = "pincode", defaultValue = "") var pincode:String="",
    @ColumnInfo(name = "city_id", defaultValue = "") var city_id:String="",
    @ColumnInfo(name = "city_name", defaultValue = "") var city_name:String="",
    @ColumnInfo(name = "state_id", defaultValue = "") var state_id:String="",
    @ColumnInfo(name = "state_name", defaultValue = "") var state_name:String=""
)

@Dao
interface PinStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj:PinStateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objL: List<PinStateEntity>)

    @Query("delete from MR_PIN_STATE")
    suspend fun deleteAll()

    @Query("select * from MR_PIN_STATE")
    suspend fun getAll() : List<PinStateEntity>

    @Query("select COALESCE(state_id,null,'0') as stateID from MR_PIN_STATE where pincode = :pincode")
    suspend fun getStateIDByPinCode(pincode:String) : String
}