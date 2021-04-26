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
      val selectedPositions=HashMap<Int,Int>()
   private val selectedCombinations=ArrayList<Combination<String>>()
    private val givenExclusionCombinations=ArrayList<Combination<String>>()
     private lateinit var isValidCombination:(isValid:Boolean)->Unit


    fun getPhoneDisplayList( context: Context, success:()-> Unit, failure:()-> Unit,
                                                            isValidCombination:(isValid:Boolean)->Unit){

        this.isValidCombination=isValidCombination

        //starting coroutine for fetching mobile data from web server
        viewModelScope.launch {
            try {
                 phoneData=PhoneRepos().getPhoneData()
                phoneData?.let {data->
                    dataArrangementOnSuccess(context,data)
                    success()
                } ?: run{

                    failure()
                }
            }

            catch (e:Exception){

                failure()
            }

        }
    }


    private fun dataArrangementOnSuccess(context:Context,data:MobileDataClass){
        clearData()
        var counter=0

        //fetching option list
        for (feature in data.features){

            for (option in feature.options) {
                option.featureId = feature.feature_id
                option.featurePosition=counter
            }
            counter++
            optionsList.add(feature.options)
        }

        //creating adapters for displaying option for features
        for (options in optionsList){
            adapterArrayList.add(MobileAdapter(options,context)
            { featureId,optionId,featurePos,optionPos -> dataSelected(featureId,optionId,featurePos,optionPos) })
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

   private fun dataSelected(selectedFeature:String,selectedOption:String,featurePos:Int,optionPos:Int){

            selectedFeatureAndOption[selectedFeature] = selectedOption
            selectedPositions[featurePos] = optionPos

        if(adapterArrayList.size==selectedFeatureAndOption.size){
            selectedCombinations.clear()
            checkForExclusions()
        }
    }

    private fun checkForExclusions(){
        createCombination()
        compareCombinations()
    }

    private fun createCombination(){
        //creating combination for selected options
        for (x in 0 until selectedFeatureAndOption.size){

            for (y in x+1 until selectedFeatureAndOption.size){
                var xKey=selectedFeatureAndOption.keys.toTypedArray()[x]
                var yKey=selectedFeatureAndOption.keys.toTypedArray()[y]
                selectedCombinations.add(Combination(selectedFeatureAndOption.getValue(xKey),
                                                        selectedFeatureAndOption.getValue(yKey) ))

            }
        }
    }

    private fun compareCombinations(){

            //compare selected and given combination
            for (select in selectedCombinations){
                for (given in givenExclusionCombinations){
                    if(select.isEquals(given)){
                        //not valid combination
                        isValidCombination(false)
                        return
                    }

                }
            }

            //valid combination- show detail
            isValidCombination(true)

    }

            private fun clearData(){
                optionsList.clear()
                adapterArrayList.clear()
                givenExclusionCombinations.clear()
                selectedFeatureAndOption.clear()
                selectedPositions.clear()
            }


}