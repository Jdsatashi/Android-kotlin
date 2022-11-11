package com.example.coursework.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coursework.model.CostModel
import com.example.coursework.model.TripModel

@Database(
    entities = [
        TripModel::class,
        CostModel::class
    ],
    version = 6,
)
abstract class ProjectDb : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun costDao(): CostDao

    companion object{
        @Volatile
        private var INSTANCE: ProjectDb? = null

        fun getDb(context: Context) : ProjectDb {
            val tempInstant = INSTANCE
            if(tempInstant != null){
                return tempInstant
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDb::class.java,
                    "project_db",
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}