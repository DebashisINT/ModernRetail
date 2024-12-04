package com.example.xst

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.modernretail.database.StoreDao
import com.example.modernretail.database.StoreEntity

@Database(entities = [StoreEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract val storeDao: StoreDao

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