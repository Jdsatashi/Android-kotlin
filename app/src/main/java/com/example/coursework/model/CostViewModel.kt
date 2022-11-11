package com.example.coursework.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.coursework.data.ProjectDb
import com.example.coursework.repository.CostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CostViewModel(application: Application) : AndroidViewModel(application) {
    val showCostData: LiveData<List<CostModel>>
    private val costRepository: CostRepository
    init {
        val costDao = ProjectDb.getDb(application).costDao()
        costRepository = CostRepository(costDao)
        showCostData = costRepository.showCostData
    }

    fun addCost(costModel: CostModel){
        viewModelScope.launch(Dispatchers.IO){
            costRepository.addCost(costModel)
        }
    }
    fun getExpenseByTrip(nameTrip: String) : LiveData<List<CostModel>>{
        return costRepository.getExpenseByTrip(nameTrip)
    }
}