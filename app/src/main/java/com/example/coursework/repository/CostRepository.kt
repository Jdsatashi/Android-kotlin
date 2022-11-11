package com.example.coursework.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.coursework.data.CostDao
import com.example.coursework.model.CostModel

class CostRepository(private val costDao: CostDao) {
    val showCostData: LiveData<List<CostModel>> = costDao.showCostData()

    suspend fun addCost(costModel: CostModel){
        costDao.addCost(costModel)
    }
    @WorkerThread
    fun getExpenseByTrip(nameTrip: String) : LiveData<List<CostModel>>{
        return costDao.getExpenseByTrip(nameTrip)
    }
}