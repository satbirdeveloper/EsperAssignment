package com.example.esperapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.esperapp.R
import com.example.esperapp.adapters.MainAdapter
import com.example.esperapp.adapters.MobileAdapter
import com.example.esperapp.databinding.ActivityMainBinding
import com.example.esperapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mainBinding:ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        setUpRecyclerView()
        setUpListeners()
        firePhoneApi()
    }

    private fun setUpRecyclerView(){
        mainAdapter= MainAdapter(mainViewModel.adapterArrayList)
        mainBinding.mainRecyclerView.adapter=mainAdapter
    }

    private fun setUpListeners(){
        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            firePhoneApi()
        }

        mainBinding.submitButton.setOnClickListener {

        }
    }

    private fun firePhoneApi(){
        mainBinding.swipeRefreshLayout.isRefreshing=true

        mainViewModel.getPhoneDisplayList(this,{mainAdapter.notifyDataSetChanged()},

            {Snackbar.make(
                mainBinding.swipeRefreshLayout,getString(
                    R.string.response_failure),Snackbar.LENGTH_LONG).show()},

            {isValid ->  displayDetail(isValid)})

        mainBinding.swipeRefreshLayout.isRefreshing=false
    }

        private fun displayDetail(isCombinationValid:Boolean){
            if (isCombinationValid){
                mainBinding.submitButton.visibility=View.VISIBLE
            }
            else{
                mainBinding.submitButton.visibility=View.GONE
                Snackbar.make(
                    mainBinding.swipeRefreshLayout,getString(
                        R.string.combination_alert),Snackbar.LENGTH_SHORT).show()
            }
        }

}