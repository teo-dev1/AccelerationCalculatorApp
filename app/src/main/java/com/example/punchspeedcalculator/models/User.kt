package com.example.punchspeedcalculator.models

import java.time.LocalDate
import java.util.*

data class User(
   val id:String="",
   val username:String="",
   val name:String="",
   val surname:String="",
   val dateOfBirth: Date=Date(),
   val sex:String="",
   val height:String="",
   val weight:String="",
   val foo:String=""
)


