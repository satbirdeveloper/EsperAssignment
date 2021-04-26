package com.example.esperapp.dataClasses

data class OptionsDataClass(val name:String,
                            val icon:String,
                            val id:String)
{
     lateinit var featureId:String
    var isSelected=false

}