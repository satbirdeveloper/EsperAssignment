package com.example.esperapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.esperapp.R
import com.example.esperapp.dataClasses.OptionsDataClass
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class MobileAdapter (var dataList:ArrayList<OptionsDataClass>,private val context:Context,
                    val optionSelected: (featureId:String,optionId:String) ->Unit )  :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var previousPos=0

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
                if ( phoneData.isSelected){
                    viewHolder.cardViewContainer.strokeColor= ContextCompat.getColor(context, R.color.purple_500)
                }
                else{
                    viewHolder.cardViewContainer.strokeColor= 0
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

   inner class PhoneDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var nameText: TextView =itemView.findViewById(R.id.nameTextView)
        var iconImageView: ImageView =itemView.findViewById(R.id.iconImageView)
       var cardViewContainer:MaterialCardView=itemView.findViewById(R.id.cardViewContainer)

        init {
            cardViewContainer.setOnClickListener {
                highlightBackground()
                optionSelected(dataList[absoluteAdapterPosition].featureId,dataList[absoluteAdapterPosition].id)
            }
        }

       private fun highlightBackground(){
           dataList[previousPos].isSelected=false
           dataList[absoluteAdapterPosition].isSelected=true
           notifyItemChanged(previousPos)
           notifyItemChanged(absoluteAdapterPosition)
           previousPos=absoluteAdapterPosition
       }

    }

}