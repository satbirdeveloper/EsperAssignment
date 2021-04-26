package com.example.esperapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.esperapp.R
import com.example.esperapp.adapters.PhoneDetailAdapter
import com.example.esperapp.dataClasses.FeaturesDataClass
import com.example.esperapp.dataClasses.MobileDataClass
import com.example.esperapp.databinding.ActivityPhoneDetailBinding
import com.example.esperapp.viewmodels.MainViewModel
import com.example.esperapp.viewmodels.PhoneDetailViewModel

class PhoneDetailActivity : AppCompatActivity() {

    private val phoneDetailViewModel by viewModels<PhoneDetailViewModel>()
    private lateinit var phoneDetailBinding: ActivityPhoneDetailBinding
    private lateinit var phoneDetailAdapter: PhoneDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                phoneDetailBinding=DataBindingUtil.setContentView(this,R.layout.activity_phone_detail)
                fetchPhoneDetailData()
                setUpRecyclerView()
    }



    private fun fetchPhoneDetailData(){
        phoneDetailViewModel.selectedPositions=intent.getSerializableExtra("SelectedPhoneData") as HashMap<Int, Int>
        phoneDetailViewModel.phoneFeatures=intent.getSerializableExtra("PhoneDetailData") as ArrayList<FeaturesDataClass>

        phoneDetailViewModel.selectedPositions?.let { positionHasMap->
            phoneDetailViewModel.phoneFeatures?.let { features->
                for (key in positionHasMap.keys){
                    phoneDetailViewModel.phoneDetailList.add(features[key].options[positionHasMap.getValue(key)])
                }

            }

        }
    }

  private fun  setUpRecyclerView(){
            phoneDetailAdapter= PhoneDetailAdapter(phoneDetailViewModel.phoneDetailList)
            phoneDetailBinding.detailRecyclerView.adapter=phoneDetailAdapter
    }


}