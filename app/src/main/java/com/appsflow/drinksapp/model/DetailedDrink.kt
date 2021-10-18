package com.appsflow.drinksapp.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class DetailedDrink(
    val idDrink: String,
    @SerializedName("strDrink") val title: String,
    val strAlcoholic: String,
    val strCategory: String,
    val strDrinkThumb: String,
    val strGlass: String,
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strIngredient10: String,
    val strInstructions: String,
)