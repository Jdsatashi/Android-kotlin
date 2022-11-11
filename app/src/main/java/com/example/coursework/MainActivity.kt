package com.example.coursework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coursework.activities.CostActivity
import com.example.coursework.activities.TripActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabTrip.setOnClickListener{
            val intentTrip = Intent(this, TripActivity::class.java)
            startActivity(intentTrip)
        }

        fabCost.setOnClickListener{
            val intentCost = Intent(this, CostActivity::class.java)
            startActivity(intentCost)
        }
    }
}