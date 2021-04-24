package com.example.esperapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esperapp.R
import com.example.esperapp.dataClasses.OptionsDataClass
import com.squareup.picasso.Picasso

class MobileAdapter (var dataList:ArrayList<OptionsDataClass> )  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView= LayoutInflater.from(parent.context).
        inflate(R.layout.phone_data_row,parent,false)
        return PhoneDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhoneDataViewHolder).also {viewHolder->
            dataList[position].let { phoneData ->
                viewHolder.nameText.text=phoneData.name
                Picasso.get().load(phoneData.icon).into(viewHolder.iconImageView)
            }

        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class PhoneDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameText: TextView =itemView.findViewById(R.id.nameTextView)
        var iconImageView: ImageView =itemView.findViewById(R.id.iconImageView)

    }

}