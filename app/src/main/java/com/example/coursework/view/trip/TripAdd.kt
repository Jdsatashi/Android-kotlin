package com.example.coursework.view.trip

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.coursework.R
import com.example.coursework.model.TripModel
import com.example.coursework.model.TripViewModel
import kotlinx.android.synthetic.main.trip_add_fragment.*
import kotlinx.android.synthetic.main.trip_add_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*

class TripAdd : Fragment() {
    private lateinit var tvDate: TextView
    private lateinit var btnDate : Button

    lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.trip_add_fragment, container, false)

        mTripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)

        view.btnAddTrip.setOnClickListener{
            insertTripData()
        }
        
        tvDate = view.et_tdate
        btnDate = view.btn_date

        val myCalendar = Calendar.getInstance()

        val datePicked = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        btnDate.setOnClickListener{
            DatePickerDialog(requireContext(), datePicked,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        return view
    }

    private fun insertTripData() {
        val name = et_tname.text.toString()
        val vehicle = et_tvehicle.text.toString()
        val date = et_tdate.text.toString()
        val start = et_start.text.toString()
        val to = et_goto.text.toString()
        val description = et_desc.text.toString()
        val risk = cb_risk.isChecked

        if(name.isNotEmpty() && vehicle.isNotEmpty() && date.isNotEmpty() &&
            start.isNotEmpty() && to.isNotEmpty()
        ){
            val trip = TripModel(name, vehicle, date, start, to, risk, description)

            var message = "Trip's name: $name"
            message += "\n Vehicle: $vehicle"
            message += "\n Date: $date"
            message += "\n Start from: $start"
            message += "\n Go to: $to"
            if(description.isNotEmpty()){
                message += "\n Description: $description"
            }
            message += "\n Risk assessment: $risk"

            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                mTripViewModel.addTrip(trip)
                Toast.makeText(
                    requireContext(),
                    "Successful adding trip",
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_tripAdd_to_tripHome)
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Confirm your data?")
            builder.setMessage(message)
            builder.create().show()
        } else {
            if(name.isEmpty()){
                et_tname.error = "Name got error, try again"
            }
            if(vehicle.isEmpty()){
                et_tvehicle.error = "Vehicle can not empty"
            }
            if(date.isEmpty()){
                et_tdate.error = "Date can not empty"
            }
            if(start.isEmpty()){
                et_start.error = "Start location can not empty"
            }
            if(to.isEmpty()){
                et_goto.error = "Destination can not empty"
            }
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val format = "dd-mm-yyyy"
        val dateFormat = SimpleDateFormat(format, Locale.UK)
        tvDate.setText(dateFormat.format(myCalendar.time))
    }
}