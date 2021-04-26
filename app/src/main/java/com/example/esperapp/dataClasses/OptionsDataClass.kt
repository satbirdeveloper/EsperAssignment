package com.example.esperapp.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OptionsDataClass(val name:String,
                            val icon:String,
                            val id:String):Parcelable
{
     lateinit var featureId:String
    var featurePosition=0
    var isSelected=false

}