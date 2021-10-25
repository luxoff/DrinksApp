package com.appsflow.drinksapp.model


import com.google.gson.annotations.SerializedName

data class Client(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)