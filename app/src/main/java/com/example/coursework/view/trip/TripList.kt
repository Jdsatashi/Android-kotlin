package com.example.coursework.view.trip

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.model.TripModel
import kotlinx.android.synthetic.main.trip_list_adapter.view.*

class TripList : RecyclerView.Adapter<TripList.MyViewHolder>() {
    private var tripList = emptyList<TripModel>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.trip_list_adapter, parent, false))
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = tripList[position]

        holder.itemView.t_name.text = currentItem.name
        holder.itemView.t_vehicle.text = currentItem.vehicle
        holder.itemView.t_date.text = currentItem.date
        holder.itemView.t_start.text = currentItem.start
        holder.itemView.t_goto.text = currentItem.to
        holder.itemView.t_risk.text = currentItem.risk.toString()
        if(currentItem.description.isEmpty()){
            holder.itemView.t_desc.text = "No"
        } else {
            holder.itemView.t_desc.text = currentItem.description
        }

        holder.itemView.btn_updTrip.setOnClickListener{
            val action = TripHomeDirections.actionTripHomeToTripUpdate(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.btn_addExpense.setOnClickListener{
            val action = TripHomeDirections.actionTripHomeToCostAdd2(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.itemView.btn_viewExpense.setOnClickListener{
            val action = TripHomeDirections.actionTripHomeToCostHome2(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }


    fun setData(trip: List<TripModel>){
        this.tripList = trip
        notifyDataSetChanged()
    }
}