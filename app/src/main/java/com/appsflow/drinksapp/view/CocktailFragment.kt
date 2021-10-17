package com.appsflow.drinksapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.FragmentCocktailBinding

class CocktailFragment : Fragment(R.layout.fragment_cocktail) {
    private lateinit var binding: FragmentCocktailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCocktailBinding.bind(view)
    }

}