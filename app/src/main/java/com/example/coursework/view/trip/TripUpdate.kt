package com.example.coursework.view.trip

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coursework.R
import com.example.coursework.model.TripModel
import com.example.coursework.model.TripViewModel
import kotlinx.android.synthetic.main.trip_update_fragment.*
import kotlinx.android.synthetic.main.trip_update_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*

class TripUpdate : Fragment() {
    private val args by navArgs<TripUpdateArgs>()
    private lateinit var tvDate: TextView
    private lateinit var btnDate : Button
    lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.trip_update_fragment, container, false)

        view.upd_tname.setText(args.currentTrip.name)
        view.upd_vehicle.setText(args.currentTrip.vehicle)
        view.upd_tdate.setText(args.currentTrip.date)
        view.upd_start.setText(args.currentTrip.start)
        view.upd_goto.setText(args.currentTrip.to)
        view.upd_desc.setText(args.currentTrip.description)

        mTripViewModel = ViewModelProvider(this)[TripViewModel::class.java]

        tvDate = view.upd_tdate
        btnDate = view.btn_updDate

        val myCalendar = Calendar.getInstance()

        val datePicked = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
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

        view.btnUpdateTrip.setOnClickListener{
            val vehicle = upd_vehicle.text.toString()
            val date = upd_tdate.text.toString()
            val start = upd_start.text.toString()
            val to = upd_goto.text.toString()
            val description = upd_desc.text.toString()
            val risk = upd_risk.isChecked

            if(vehicle.isNotEmpty() && date.isNotEmpty() &&
                start.isNotEmpty() && to.isNotEmpty()
            ) {
                val updated = TripModel(
                    args.currentTrip.name, vehicle, date, start, to, risk, description
                )

                mTripViewModel.updateTrip(updated)
                Toast.makeText(
                    requireContext(),
                    "Successful updated",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_tripUpdate_to_tripHome)
            }else {
                if (vehicle.isEmpty()) {
                    upd_vehicle.error = "Vehicle can not empty"
                }
                if (date.isEmpty()) {
                    upd_tdate.error = "Date can not empty"
                }
                if (start.isEmpty()) {
                    upd_start.error = "Start location can not empty"
                }
                if (to.isEmpty()) {
                    upd_goto.error = "Destination can not empty"
                }
            }
        }

        view.fab_deleteTrip.setOnClickListener{
            deleteTrip()
        }

        return view
    }

    private fun deleteTrip(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTripViewModel.deleteTrip(args.currentTrip)
            Toast.makeText(
                requireContext(),
                "Successful removed: ${args.currentTrip.name}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_tripUpdate_to_tripHome)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentTrip.name}?")
        builder.setMessage("Are you sure to delete ${args.currentTrip.name}?")
        builder.create().show()
    }

    private fun updateLable(myCalendar: Calendar) {
        val format = "dd-MM-YYYY"
        val dateFormat = SimpleDateFormat(format, Locale.UK)
        tvDate.text = dateFormat.format(myCalendar.time)
    }
}