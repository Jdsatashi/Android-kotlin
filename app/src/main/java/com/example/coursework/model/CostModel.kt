package com.example.coursework.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cost_table"
    ,
    foreignKeys = [ForeignKey(
        entity = TripModel::class,
        childColumns = ["trip_name"],
        parentColumns = ["name"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CostModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val amount: String,
    val type: String,
    val trip_name: String,
    val comment: String,
    val time: String,
    )