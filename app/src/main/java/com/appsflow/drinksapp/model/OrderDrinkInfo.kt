package com.appsflow.drinksapp.model


import com.google.gson.annotations.SerializedName

data class OrderDrinkInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Price
)