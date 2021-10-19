package com.appsflow.drinksapp.model


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("client")
    val client: Client,
    @SerializedName("orderDate")
    val orderDate: String,
    @SerializedName("orders")
    val orders: List<OrderDrinkInfo>
)