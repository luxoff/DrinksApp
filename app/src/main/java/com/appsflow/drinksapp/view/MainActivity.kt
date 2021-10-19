package com.appsflow.drinksapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.ActivityMainBinding
import com.appsflow.drinksapp.model.retrofit.ApiInterface
import com.appsflow.drinksapp.model.retrofit.DrinkService
import kotlinx.coroutines.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drinkService: ApiInterface
    private lateinit var navController: NavController

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DrinksApp)
        super.onCreate(savedInstanceState)
        drinkService = DrinkService().getDrinkService()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpNavigation()

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.ordinary_drinks_fragment,
                R.id.cocktails_fragment
            )
            .build()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.apply {
            fabRandomDrink.setOnClickListener {
                GlobalScope.launch {
                    randomDrink(binding)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

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