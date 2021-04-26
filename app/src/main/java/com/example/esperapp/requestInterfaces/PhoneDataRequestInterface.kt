package com.example.esperapp.requestInterfaces

import com.example.esperapp.dataClasses.MobileDataClass
import retrofit2.http.GET

interface PhoneDataRequestInterface {

    @GET("mhrpatel12/esper-assignment/db")
    suspend fun getPhoneData(): MobileDataClass?
}