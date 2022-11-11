package com.example.coursework.view.cost

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.model.CostModel
import kotlinx.android.synthetic.main.cost_list.view.*

class CostList : RecyclerView.Adapter<CostList.CostViewHolder>() {
    private var costList = emptyList<CostModel>()
    class CostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostViewHolder {
        return CostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cost_list, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CostViewHolder, position: Int) {
        val currentCost = costList[position]

        holder.itemView.t_id.text = currentCost.id.toString() + ". "
        holder.itemView.t_EbTtime.text = currentCost.trip_name
        holder.itemView.t_EbTamount.text = currentCost.amount
        holder.itemView.t_EbTtype.text = currentCost.type
        holder.itemView.t_EbTcomment.text = currentCost.comment
        holder.itemView.t_dateTime.text = currentCost.time
    }

    override fun getItemCount(): Int {
        return costList.size
    }

    fun setData(cost: List<CostModel>){
        this.costList = cost
        notifyDataSetChanged()
    }
}