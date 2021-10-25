package com.appsflow.drinksapp.model

import com.google.gson.annotations.SerializedName

data class ResultList(
    @SerializedName("drinks") val drinkList: List<Drink>
)