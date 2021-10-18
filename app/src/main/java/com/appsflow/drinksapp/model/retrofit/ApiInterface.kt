package com.appsflow.drinksapp.model.retrofit

import com.appsflow.drinksapp.model.DetailedDrinkList
import com.appsflow.drinksapp.model.Drink
import com.appsflow.drinksapp.model.ResultList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/json/v1/{API_KEY}/filter.php?c=Ordinary_Drink")
    suspend fun getOrdinaryDrinks(@Path("API_KEY") apiKey: Int): Response<ResultList>

    @GET("api/json/v1/{API_KEY}/filter.php?c=Cocktail")
    suspend fun getCocktails(@Path("API_KEY") apiKey: Int): Response<ResultList>

    @GET("api/json/v1/{API_KEY}/lookup.php") // Retrofit will put "?i={id}" as a query
    suspend fun lookupDrinkById(
        @Path("API_KEY") apiKey: Int,
        @Query("i") idDrink: String
    ): Response<DetailedDrinkList>
}