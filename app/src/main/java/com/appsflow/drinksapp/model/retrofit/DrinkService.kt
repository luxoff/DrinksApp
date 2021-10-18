package com.appsflow.drinksapp.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DrinkService {

    fun getDrinkService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    companion object {
        private const val BASE_URL = "https://thecocktaildb.com/"
    }
}