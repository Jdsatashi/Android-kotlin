package com.example.coursework.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.coursework.data.ProjectDb
import com.example.coursework.repository.TripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {
    val showTripData: LiveData<List<TripModel>>
    private val repository:TripRepository
    init {
        val tripDao = ProjectDb.getDb(application).tripDao()
        repository = TripRepository(tripDao)
        showTripData = repository.showTripData
    }

    fun addTrip(tripModel: TripModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrip(tripModel)
        }
    }

    fun updateTrip(tripModel: TripModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTrip(tripModel)
        }
    }

    fun deleteTrip(tripModel: TripModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrip(tripModel)
        }
    }

    fun searchTrip(query: String) : LiveData<List<TripModel>>{
        return repository.searchTrip(query)
    }

    fun deleteAllTrip(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTrip()
        }
    }

}