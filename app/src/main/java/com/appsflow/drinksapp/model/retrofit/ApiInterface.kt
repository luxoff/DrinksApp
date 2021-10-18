package com.appsflow.drinksapp.model.retrofit

import com.appsflow.drinksapp.model.Drink
import com.appsflow.drinksapp.model.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("api/json/v1/{API_KEY}/filter.php?c=Ordinary_Drink")
    suspend fun getOrdinaryDrinks(@Path("API_KEY") apiKey: Int): Response<ResultList>

    @GET("api/json/v1/{API_KEY}/filter.php?c=Cocktail")
    suspend fun getCocktails(@Path("API_KEY") apiKey: Int): Response<ResultList>

}