package com.appsflow.drinksapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.FragmentOrdinaryDrinkBinding
import com.appsflow.drinksapp.model.Drink
import com.appsflow.drinksapp.model.retrofit.ApiInterface
import com.appsflow.drinksapp.model.retrofit.DrinkService
import com.appsflow.drinksapp.view.adapter.DrinksListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.lang.Exception

class OrdinaryDrinkFragment : Fragment(R.layout.fragment_ordinary_drink),
    DrinksListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentOrdinaryDrinkBinding
    private lateinit var drinkService: ApiInterface
    private var drinksList: List<Drink>? = null

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
                fetchOrdinaryDrinks(this@apply)
            }

            swipeRefreshLayoutOrdinary.setOnRefreshListener {
                swipeRefreshLayoutOrdinary.isRefreshing = true
                GlobalScope.launch(Dispatchers.IO) {
                    fetchOrdinaryDrinks(this@apply)
                }
                swipeRefreshLayoutOrdinary.isRefreshing = false
            }
        }
    }

    suspend fun fetchOrdinaryDrinks(binding: FragmentOrdinaryDrinkBinding) {
        binding.apply {
            val response = drinkService.getOrdinaryDrinks(API_KEY)
            if (response.isSuccessful) {
                val drinks = response.body()?.drinkList
                drinksList = response.body()?.drinkList
                withContext(Dispatchers.Main) {
                    try {
                        rvOrdinaryDrinks.adapter = drinks?.let {
                            DrinksListAdapter(
                                it,
                                this@OrdinaryDrinkFragment
                            )
                        }
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
    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)
        val item = drinksList?.get(position)
        val idDrink = item?.idDrink
        if (idDrink != null) {
            val action = DetailsFragmentDirections.actionGlobalDetailsFragment(idDrink)
            findNavController().navigate(action)
        }
    }

    companion object {
        const val API_KEY = 1
    }

}
