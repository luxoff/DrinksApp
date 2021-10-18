package com.appsflow.drinksapp.model

import com.google.gson.annotations.SerializedName

data class DetailedDrinkList(
    @SerializedName("drinks") val drinkInfo: List<DetailedDrink>
)