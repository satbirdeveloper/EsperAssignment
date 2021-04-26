package com.example.esperapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.esperapp.dataClasses.FeaturesDataClass
import com.example.esperapp.dataClasses.MobileDataClass
import com.example.esperapp.dataClasses.OptionsDataClass

class PhoneDetailViewModel:ViewModel() {

      var selectedPositions:HashMap<Int,Int>?=null
      var phoneFeatures: ArrayList<FeaturesDataClass>?=null
        val phoneDetailList=ArrayList<OptionsDataClass>()
}