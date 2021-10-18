package com.appsflow.drinksapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.FragmentOrdinaryDrinkBinding
import com.appsflow.drinksapp.model.retrofit.ApiInterface
import com.appsflow.drinksapp.model.retrofit.DrinkService
import com.appsflow.drinksapp.view.adapter.DrinksListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.lang.Exception

class OrdinaryDrinkFragment : Fragment(R.layout.fragment_ordinary_drink) {
    private lateinit var binding: FragmentOrdinaryDrinkBinding
    private lateinit var drinkService: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinkService = DrinkService().getDrinkService()
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdinaryDrinkBinding.bind(view)

        binding.apply {
            GlobalScope.launch(Dispatchers.IO) {
                val response = drinkService.getOrdinaryDrinks(API_KEY)
                if (response.isSuccessful) {
                    val drinks = response.body()?.drinkList
                    withContext(Dispatchers.Main) {
                        try {
                            rvOrdinaryDrinks.adapter = drinks?.let { DrinksListAdapter(it) }
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong!", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Retrofit response failed", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            swipeRefreshLayoutOrdinary.setOnRefreshListener {
                swipeRefreshLayoutOrdinary.isRefreshing = true
                GlobalScope.launch(Dispatchers.IO) {
                    val response = drinkService.getOrdinaryDrinks(API_KEY)
                    if (response.isSuccessful) {
                        val drinks = response.body()?.drinkList
                        withContext(Dispatchers.Main) {
                            try {
                                rvOrdinaryDrinks.adapter = drinks?.let { DrinksListAdapter(it) }
                                rvOrdinaryDrinks.adapter?.notifyDataSetChanged()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    requireContext(),
                                    "Something went wrong!", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Retrofit response failed", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                swipeRefreshLayoutOrdinary.isRefreshing = false
            }
        }
    }

    companion object {
        const val API_KEY = 1
    }

}
