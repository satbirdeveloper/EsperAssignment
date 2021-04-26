package com.example.esperapp.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeaturesDataClass(val feature_id:String,
                        val name:String,
                        val options:ArrayList<OptionsDataClass>) : Parcelable