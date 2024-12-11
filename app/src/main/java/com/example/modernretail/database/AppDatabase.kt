package com.example.xst

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.modernretail.database.PinStateDao
import com.example.modernretail.database.PinStateEntity
import com.example.modernretail.database.StoreDao
import com.example.modernretail.database.StoreEntity
import com.example.modernretail.database.StoreTypeDao
import com.example.modernretail.database.StoreTypeEntity

@Database(entities = [StoreEntity::class,StoreTypeEntity::class,PinStateEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract val storeDao: StoreDao
    abstract val storeTypeDao: StoreTypeDao
    abstract val pinStateDao: PinStateDao

    companion object{
        private var INSTANCE : AppDatabase? = null
        fun getDatabase(context: Context):AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context,AppDatabase::class.java,"app_database").build()
                }
                INSTANCE = instance
                return INSTANCE!!
            }
        }
    }

}