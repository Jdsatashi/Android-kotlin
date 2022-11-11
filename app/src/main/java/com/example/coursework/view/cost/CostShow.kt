package com.example.coursework.view.cost

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursework.MainActivity
import com.example.coursework.R
import com.example.coursework.model.CostViewModel
import kotlinx.android.synthetic.main.cost_show_fragment.*
import kotlinx.android.synthetic.main.cost_show_fragment.view.*

class CostShow : Fragment() {
    private lateinit var mCostViewModel: CostViewModel
    private val args by navArgs<CostShowArgs>()
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.cost_show_fragment, container, false)

        view.fab_costToHome.setOnClickListener{
            val intent = Intent(this@CostShow.context, MainActivity::class.java)
            startActivity(intent)
        }

        mCostViewModel = ViewModelProvider(this).get(CostViewModel::class.java)

        val adapter = CostList()
        val rv = view.rv_cost
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        mCostViewModel.showCostData.observe(viewLifecycleOwner, Observer { trip ->
            adapter.setData(trip)
        })

        view.textView4.text = "Expenses of " + args.currentTrip.name

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

    private fun getItemsFromDb(tripName: String) {
        var tripName: String
        val trip = args.currentTrip.name
        tripName = "%$trip%"

        val adapter = CostList()
        val rv = rv_cost
        rv.adapter = adapter

        mCostViewModel.getExpenseByTrip(nameTrip = tripName).observe(this, Observer { cost ->
            cost?.let {
                Log.e("List = ", cost.toString())
            }
            adapter.setData(cost)
        })
    }
}