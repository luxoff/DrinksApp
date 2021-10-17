package com.appsflow.drinksapp.model.retrofit

import com.appsflow.drinksapp.model.Drink
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("api/json/v1/{API_KEY}/filter.php?c=Ordinary_Drink")
    suspend fun getOrdinaryDrinks(@Path("API_KEY") apiKey: String) : Call<List<Drink>>

    @GET("api/json/v1/{API_KEY}/filter.php?c=Cocktail")
    suspend fun getCocktails(@Path("API_KEY") apiKey: String) : Call<List<Drink>>

    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://thecocktaildb.com/"

        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}