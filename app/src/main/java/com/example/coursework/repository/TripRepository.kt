package com.example.coursework.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.coursework.data.TripDao
import com.example.coursework.model.CostModel
import com.example.coursework.model.TripModel

class TripRepository(private val tripDao: TripDao) {
    val showTripData: LiveData<List<TripModel>> =tripDao.showTripData()

    suspend fun addTrip(tripModel: TripModel){
        tripDao.addTrip(tripModel)
    }

    suspend fun updateTrip(tripModel: TripModel){
        tripDao.updateTrip(tripModel)
    }

    suspend fun deleteTrip(tripModel: TripModel){
        tripDao.deleteTrip(tripModel)
    }

    @WorkerThread
    fun searchTrip(query: String) : LiveData<List<TripModel>>{
        return tripDao.searchTrip(query)
    }

    suspend fun deleteAllTrip(){
        tripDao.deleteAllTrip()
    }

}