package com.example.coursework.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coursework.model.CostModel
import com.example.coursework.model.TripModel

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrip(tripModel: TripModel)

    @Query("SELECT * FROM trip_table ORDER BY name ASC")
    fun showTripData(): LiveData<List<TripModel>>

    @Update
    suspend fun updateTrip(tripModel: TripModel)

    @Delete
    suspend fun deleteTrip(tripModel: TripModel)

//    OR date LIKE :query OR `to` LIKE :query
    @Query("SELECT * FROM trip_table WHERE name LIKE :query OR date LIKE :query OR `to` LIKE :query")
    fun searchTrip(query: String) : LiveData<List<TripModel>>

    @Query("DELETE FROM trip_table")
    suspend fun deleteAllTrip()
}