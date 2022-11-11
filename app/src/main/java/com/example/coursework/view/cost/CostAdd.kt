package com.example.coursework.view.cost

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.coursework.R
import com.example.coursework.model.CostModel
import com.example.coursework.model.CostViewModel
import com.example.coursework.view.trip.TripUpdateArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.cost_add_fragment.*
import kotlinx.android.synthetic.main.cost_add_fragment.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.exp

class CostAdd : Fragment() {
    private lateinit var mCostViewModel: CostViewModel
    private val args by navArgs<CostAddArgs>()
    private lateinit var firebase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.cost_add_fragment, container, false)

        mCostViewModel = ViewModelProvider(this)[CostViewModel::class.java]

        firebase = FirebaseDatabase.getInstance().reference.child("expense")

        view.t_nameTiE.text = args.currentTrip.name

        view.btnAddCost.setOnClickListener{
            insertCost()
        }

        return view
    }
    private fun insertCost(){
        val amount = et_amount.text.toString()
        val type = et_type.text.toString()
        val cmt = et_cmt.text.toString()
        val tripName = args.currentTrip.name
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val time = current.format(formatter)

        if(amount.isNotEmpty() && type.isNotEmpty() && tripName.isNotEmpty()){
            val expense = CostModel(0, amount, type, tripName, cmt, time)

            var message = " Expense of trip: $tripName"
            message += "\n Expense amount: $amount"
            message += "\n Expense type: $type"
            message += "\n Comment: $cmt"
            message += "\n Time: $time"

            val builder = AlertDialog.Builder(this@CostAdd.context)
            builder.setPositiveButton("Yes") { _, _ ->
                mCostViewModel.addCost(expense)
                firebase.child(tripName).child(time).setValue(expense)
                Toast.makeText(
                    requireContext(),
                    "Add expense successful.",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_costAdd2_to_tripHome)
            }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Confirm your expense?")
                builder.setMessage(message)
                builder.create().show()
        } else {
            if(amount.isEmpty()){
                et_amount.error = "Please writes the expense."
            }
            if (type.isEmpty()){
                et_type.error = "You have to write the type."
            }
            Toast.makeText(requireContext(),
                "Something wrong, try again.",
                Toast.LENGTH_LONG).show()
        }

    }
}