package com.appsflow.drinksapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.FragmentCocktailBinding
import com.appsflow.drinksapp.model.Drink
import com.appsflow.drinksapp.model.retrofit.ApiInterface
import com.appsflow.drinksapp.model.retrofit.DrinkService
import com.appsflow.drinksapp.view.adapter.DrinksListAdapter
import kotlinx.coroutines.*
import java.lang.Exception

class CocktailFragment : Fragment(R.layout.fragment_cocktail),
    DrinksListAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCocktailBinding
    private lateinit var drinkService: ApiInterface
    private var drinksList: List<Drink>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinkService = DrinkService().getDrinkService()
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCocktailBinding.bind(view)

        binding.apply {
            try {
                GlobalScope.launch(Dispatchers.IO) {
                    fetchCocktails(binding)
                }


                swipeRefreshLayoutCocktail.setOnRefreshListener {
                    swipeRefreshLayoutCocktail.isRefreshing = true

                    GlobalScope.launch(Dispatchers.IO) {
                        fetchCocktails(binding)
                        swipeRefreshLayoutCocktail.isRefreshing = false
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong. Check network connection!", Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    suspend fun fetchCocktails(binding: FragmentCocktailBinding) {
        try {
            binding.apply {
                val response = drinkService.getCocktails(API_KEY)
                if (response.isSuccessful) {
                    val drinks = response.body()?.drinkList
                    drinksList = response.body()?.drinkList
                    withContext(Dispatchers.Main) {
                        try {
                            rvCocktails.adapter =
                                drinks?.let {
                                    DrinksListAdapter(
                                        it,
                                        this@CocktailFragment
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
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong. Check network connection!", Toast.LENGTH_LONG
                ).show()
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