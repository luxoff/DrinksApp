package com.appsflow.drinksapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpNavigation();
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
}