package com.appsflow.drinksapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.ActivityMainBinding
import com.appsflow.drinksapp.model.retrofit.ApiInterface
import com.appsflow.drinksapp.model.retrofit.DrinkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drinkService: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DrinksApp)
        super.onCreate(savedInstanceState)
        drinkService = DrinkService().getDrinkService()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpNavigation()

        binding.apply {
            fabRandomDrink.setOnClickListener {
                GlobalScope.launch {
                    randomDrink(binding)
                }
            }
        }
    }

    private suspend fun randomDrink(binding: ActivityMainBinding) {
        try {
            withContext(Dispatchers.IO) {
                val response = drinkService.getRandomDrink(API_KEY)
                if (response.isSuccessful) {
                    val drink = response.body()?.drinkInfo?.get(0)
                    val id = drink?.idDrink
                    withContext(Dispatchers.Main) {
                        if (id != null) {
                            val action = DetailsFragmentDirections.actionGlobalDetailsFragment(id)
                            binding.navHostFragment.findNavController().navigate(action)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "Retrofit response failed", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "Something went wrong. Check network connection!", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUpNavigation() {
        binding.apply {
            val navController = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment)?.findNavController()!!
            NavigationUI.setupWithNavController(
                bttmNav, navController
            )
        }
    }

    companion object {
        const val API_KEY = 1
    }
}