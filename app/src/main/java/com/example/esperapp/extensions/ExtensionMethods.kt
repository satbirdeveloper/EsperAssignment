package com.example.esperapp.extensions

import com.example.esperapp.dataClasses.Combination

fun <T> Combination<T>.isEquals(given: Combination<T>):Boolean{

    if(this.first?.equals(given.first)==true && this.second?.equals(given.second)==true){
        return true
    }

    if(this.first?.equals(given.second)==true && this.second?.equals(given.first)==true){
        return true
    }

    return false

}