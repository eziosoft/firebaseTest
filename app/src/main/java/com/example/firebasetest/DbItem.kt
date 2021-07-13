package com.example.firebasetest

data class DbItem(val userName:String?=null, val number: String? = null, val name: String? = null)
{

    override fun toString():String
    {
        return "$userName: $number, $name"
    }
}