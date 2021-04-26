package com.example.esperapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esperapp.adapters.MobileAdapter
import com.example.esperapp.dataClasses.Combination
import com.example.esperapp.dataClasses.MobileDataClass
import com.example.esperapp.dataClasses.OptionsDataClass
import com.example.esperapp.extensions.isEquals
import com.example.esperapp.repositories.PhoneRepos
import kotlinx.coroutines.launch

 class MainViewModel : ViewModel() {


    var adapterArrayList=ArrayList<MobileAdapter>()
    var optionsList=ArrayList<ArrayList<OptionsDataClass>>()
    var phoneData:MobileDataClass?=null
   private val selectedFeatureAndOption=HashMap<String,String>()
   private val selectedCombinations=ArrayList<Combination<String>>()
    private val givenExclusionCombinations=ArrayList<Combination<String>>()
   private var selectedFeature=""
    private var selectedOption=""
    private lateinit var isValidCombination:(isValid:Boolean)->Unit


    fun getPhoneDisplayList( context: Context, success:()-> Unit, failure:()-> Unit,
    isValidCombination:(isValid:Boolean)->Unit){

        this.isValidCombination=isValidCombination
        viewModelScope.launch {
            try {
                 phoneData=PhoneRepos().getPhoneData()
                phoneData?.let {data->
                    dataArrangementOnSuccess(context,data)
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


    private fun dataArrangementOnSuccess(context:Context,data:MobileDataClass){
        optionsList.clear()
        adapterArrayList.clear()
        givenExclusionCombinations.clear()
        selectedFeatureAndOption.clear()

        for (feature in data.features){

            for (option in feature.options)
                option.featureId=feature.feature_id

            optionsList.add(feature.options)
        }
        for (options in optionsList){
            adapterArrayList.add(MobileAdapter(options,context) { featureId,optionId -> selectedOption=optionId
                selectedFeature=featureId
                dataSelected()})
        }

        fetchExclusionCombinations()
    }

     private fun fetchExclusionCombinations(){
         phoneData?.let { mobileDataClass ->
             //fetch given exclusion combinations
             val exclusions = mobileDataClass.exclusions
             for (exclusion in exclusions) {
                 givenExclusionCombinations.add(
                     Combination(
                         exclusion[0].options_id,
                         exclusion[1].options_id
                     )
                 )
             }
         }
     }

   private fun dataSelected(){
        Log.e("Op_Sel","Op_Sel: $selectedOption")
        Log.e("Fe_Sel","Fe_Sel: $selectedFeature")

            selectedFeatureAndOption[selectedFeature] = selectedOption

        if(adapterArrayList.size==selectedFeatureAndOption.size){
            Log.e("Do_Exclusions","Do_Exclusions")
            for (selectedData in selectedFeatureAndOption){
                Log.e("selectedKey","selectedKey: ${selectedData.key} ")
                Log.e("selectedValue","selectedValue:  ${selectedData.value}")
            }
            selectedCombinations.clear()
            checkForExclusions()
        }
    }

    private fun checkForExclusions(){
        createCombination()
        compareCombinations()
    }

    private fun createCombination(){
        for (x in 0 until selectedFeatureAndOption.size){
            for (y in x+1 until selectedFeatureAndOption.size){
                var xKey=selectedFeatureAndOption.keys.toTypedArray()[x]
                var yKey=selectedFeatureAndOption.keys.toTypedArray()[y]
                selectedCombinations.add(Combination(selectedFeatureAndOption.getValue(xKey),
                                                        selectedFeatureAndOption.getValue(yKey) ))

                Log.e("Combination","First: ${selectedFeatureAndOption.getValue(xKey)}")
                Log.e("Combination","Second: ${selectedFeatureAndOption.getValue(yKey)}")
                Log.e("----------","-------------")
            }
        }
    }

    private fun compareCombinations(){

            //compare selected and given combination
            for (select in selectedCombinations){
                for (given in givenExclusionCombinations){
                    if(select.isEquals(given)){
                        Log.e("Sorry Not Available","Sorry Not Available")
                        isValidCombination(false)
                        return
                    }

                }
            }
            //valid combination- show detail
            Log.e("Available","Available show detail")
            isValidCombination(true)

    }




}