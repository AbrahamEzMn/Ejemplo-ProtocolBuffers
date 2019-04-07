package com.abrahamezmn.github.android_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import messages.PersonOuterClass
import messages.PhoneNumberOuterClass
import messages.PhoneTypeOuterClass

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val person :  PersonOuterClass.Person = PersonOuterClass.Person.newBuilder().apply {
            name = "Abraham"
            email = "correo@electronico.com"
            addPhone(PhoneNumberOuterClass.PhoneNumber.newBuilder().apply {
                number = "456789132"
                type = PhoneTypeOuterClass.PhoneType.WORK
            })
            addPhone(PhoneNumberOuterClass.PhoneNumber.newBuilder().apply {
                number = "321"
                type = PhoneTypeOuterClass.PhoneType.HOME
            })

        }.build()

        System.out.println(person.toString())
    }
}
