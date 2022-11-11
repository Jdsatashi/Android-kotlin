package com.example.coursework.view.cost

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursework.MainActivity
import com.example.coursework.R
import com.example.coursework.model.CostViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.cost_home_fragment.view.*

class CostHome : Fragment() {
    private lateinit var mCostViewModel: CostViewModel
    private lateinit var firebase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.cost_home_fragment, container, false)


        view.fab_costToHome.setOnClickListener{
            val intent = Intent(this@CostHome.context, MainActivity::class.java)
            startActivity(intent)
        }
        mCostViewModel = ViewModelProvider(this).get(CostViewModel::class.java)

        val adapter = CostList()
        val rv = view.rv_cost
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())


        firebase = FirebaseDatabase.getInstance().reference.child("expense")

        return view
    }

}