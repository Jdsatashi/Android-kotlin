package com.example.coursework.view.trip

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursework.MainActivity
import com.example.coursework.R
import com.example.coursework.model.TripViewModel
import kotlinx.android.synthetic.main.trip_home_fragment.*
import kotlinx.android.synthetic.main.trip_home_fragment.view.*

class TripHome : Fragment() {
    private lateinit var mTripViewModel: TripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.trip_home_fragment, container, false)

        val adapter = TripList()
        val recyclerView = view.rv_trip
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mTripViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        mTripViewModel.showTripData.observe(viewLifecycleOwner, Observer { trip ->
            adapter.setData(trip)
        })

        view.fab_tripToHome.setOnClickListener{
            val intent = Intent(this@TripHome.context, MainActivity::class.java)
            startActivity(intent)
        }

        view.fab_addTrip.setOnClickListener{
            findNavController().navigate(R.id.action_tripHome_to_tripAdd)
        }

        view.btn_deleteAll.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") { _, _ ->
                mTripViewModel.deleteAllTrip()
                Toast.makeText(
                    requireContext(),
                    "Successful reset all database",
                    Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Reset all data in database?")
            builder.setMessage("Are you sure to reset?")
            builder.create().show()
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.searchtrip, menu)

                val search = menu.findItem(R.id.searchItems)
                val searchView = search.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            getItemsFromDb(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        if (query != null) {
                            getItemsFromDb(query)
                        }
                        return true
                    }
            })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.searchItems -> {
                        // clearCompletedTasks()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }
    private fun getItemsFromDb(searchText: String) {
        var searchText = searchText
        searchText = "%$searchText%"

        val adapter = TripList()
        val recyclerView = rv_trip
        recyclerView.adapter = adapter

        mTripViewModel.searchTrip(query = searchText).observe(this, Observer { trip ->
            trip?.let {
                Log.e("List = ", trip.toString())
            }
            adapter.setData(trip)
        })

    }
}


