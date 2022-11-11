package com.example.coursework.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coursework.model.CostModel

@Dao
interface CostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCost(costModel: CostModel)

    @Query("SELECT * FROM cost_table ORDER BY id ASC")
    fun showCostData(): LiveData<List<CostModel>>

    @Query("SELECT * FROM cost_table WHERE trip_name LIKE :nameTrip")
    fun getExpenseByTrip(nameTrip: String) : LiveData<List<CostModel>>
}