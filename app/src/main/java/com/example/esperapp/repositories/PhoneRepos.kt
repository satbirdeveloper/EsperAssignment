package com.example.esperapp.repositories

import com.example.esperapp.dataClasses.MobileDataClass
import com.example.esperapp.networking.NetworkAccess
import com.example.esperapp.requestInterfaces.PhoneDataRequestInterface

class PhoneRepos {

    suspend fun getPhoneData():MobileDataClass?{
        return NetworkAccess.createRetrofit().create(PhoneDataRequestInterface::class.java).getPhoneData()
    }
}