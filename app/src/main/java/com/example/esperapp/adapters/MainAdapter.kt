package com.example.esperapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esperapp.R

class MainAdapter(var adapterList:ArrayList<MobileAdapter> )  :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView= LayoutInflater.from(parent.context).
        inflate(R.layout.main_phone_row,parent,false)
        return MainDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MainDataViewHolder).also {viewHolder->
            adapterList[position].let { adapter ->
                viewHolder.recyclerView.adapter=adapter
            }

        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    class MainDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var recyclerView: RecyclerView =itemView.findViewById(R.id.optionListRecyclerView)
    }
}