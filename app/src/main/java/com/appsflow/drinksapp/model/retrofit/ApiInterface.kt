package com.appsflow.drinksapp.model.retrofit

import com.appsflow.drinksapp.model.Drink
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("api/json/v1/{API_KEY}/filter.php?c=Ordinary_Drink")
    fun getOrdinaryDrinks(@Path("API_KEY") apiKey: String) : Call<List<Drink>>

    @GET("api/json/v1/{API_KEY}/filter.php?c=Cocktail")
    fun getCocktails(@Path("API_KEY") apiKey: String) : Call<List<Drink>>

}