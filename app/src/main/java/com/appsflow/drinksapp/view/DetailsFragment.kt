package com.appsflow.drinksapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.FragmentDetailsBinding
import com.appsflow.drinksapp.model.retrofit.ApiInterface
import com.appsflow.drinksapp.model.retrofit.DrinkService
import com.appsflow.drinksapp.view.adapter.DrinksListAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs<DetailsFragmentArgs>()
    private lateinit var drinkService: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drinkService = DrinkService().getDrinkService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)

        try {
            GlobalScope.launch(Dispatchers.IO) {
                fetchDrinkDetails(args.idDrink, binding)
            }

            binding.swipeRefreshLayoutDetails.setOnRefreshListener {
                binding.swipeRefreshLayoutDetails.isRefreshing = true
                GlobalScope.launch(Dispatchers.IO) {
                    fetchDrinkDetails(args.idDrink, binding)
                }
                binding.swipeRefreshLayoutDetails.isRefreshing = false
            }
        } catch (ex: Exception) {
            Toast.makeText(
                requireContext(),
                "Something went wrong. Check network connection!", Toast.LENGTH_LONG
            ).show()
        }
    }

    suspend fun fetchDrinkDetails(idDrink: String, binding: FragmentDetailsBinding) {
        try {
            binding.apply {
                withContext(Dispatchers.IO) {
                    val response = drinkService.lookupDrinkById(API_KEY, idDrink)
                    if (response.isSuccessful) {
                        val detailedInfo = response.body()?.drinkInfo?.get(0)
                        withContext(Dispatchers.Main) {
                            try {
                                Picasso.get()
                                    .load(detailedInfo?.strDrinkThumb)
                                    .placeholder(R.drawable.ic_drink_placeholder)
                                    .error(R.drawable.ic_error_placeholder)
                                    .resize(256, 256)
                                    .centerCrop()
                                    .into(ivImage)

                                val ingredients: String = "${detailedInfo?.strIngredient1} " +
                                        "${detailedInfo?.strIngredient2 ?: ""} " +
                                        "${detailedInfo?.strIngredient3 ?: ""} " +
                                        "${detailedInfo?.strIngredient4 ?: ""} " +
                                        "${detailedInfo?.strIngredient5 ?: ""} " +
                                        "${detailedInfo?.strIngredient6 ?: ""} " +
                                        "${detailedInfo?.strIngredient7 ?: ""} " +
                                        "${detailedInfo?.strIngredient8 ?: ""} " +
                                        "${detailedInfo?.strIngredient9 ?: ""} " +
                                        (detailedInfo?.strIngredient10 ?: "")

                                tvTitle.text = detailedInfo?.title
                                tvCategory.text = detailedInfo?.strCategory
                                tvAlcoholic.text = detailedInfo?.strAlcoholic
                                tvId.text = "ID: ${detailedInfo?.idDrink}"
                                tvGlass.text = detailedInfo?.strGlass
                                tvIngredients.text = ingredients
                                tvInstructions.text = detailedInfo?.strInstructions
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
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong. Check network connection!", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        const val API_KEY = 1
    }

}