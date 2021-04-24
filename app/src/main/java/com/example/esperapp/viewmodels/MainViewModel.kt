package com.example.esperapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esperapp.adapters.MobileAdapter
import com.example.esperapp.dataClasses.MobileDataClass
import com.example.esperapp.dataClasses.OptionsDataClass
import com.example.esperapp.repositories.PhoneRepos
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    var adapterArrayList=ArrayList<MobileAdapter>()
    var optionsList=ArrayList<ArrayList<OptionsDataClass>>()
    var phoneData:MobileDataClass?=null

    fun getPhoneDisplayList(success:()-> Unit , failure:()-> Unit){
        viewModelScope.launch {
            try {
                 phoneData=PhoneRepos().getPhoneData()
                phoneData?.let {data->
                    optionsList.clear()
                    adapterArrayList.clear()
                    for (feature in data.features){
                        optionsList.add(feature.options)
                    }
                    for (options in optionsList){
                        adapterArrayList.add(MobileAdapter(options))
                    }

                    success()

                } ?: run{
                    Log.e("Data Failure","Data Failure")
                     failure()
                }
            }

            catch (e:Exception){
                Log.e("Network Failure","Exception: ${e.message}")
                failure()
            }

        }
    }



}