package com.example.domain.entities

import java.io.Serializable


data class Filter(val name : String, val description: String) : Serializable {

    var id : Long? = null
}