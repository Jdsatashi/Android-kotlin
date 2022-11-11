package com.example.coursework.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "trip_table")
data class TripModel(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val vehicle: String,
    val date: String,
    val start: String,
    val to: String,
    val risk: Boolean,
    val description: String
) : Parcelable