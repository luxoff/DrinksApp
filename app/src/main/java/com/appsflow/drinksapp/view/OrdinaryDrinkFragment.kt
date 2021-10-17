package com.appsflow.drinksapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.FragmentOrdinaryDrinkBinding

class OrdinaryDrinkFragment : Fragment(R.layout.fragment_ordinary_drink) {
    private lateinit var binding: FragmentOrdinaryDrinkBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdinaryDrinkBinding.bind(view)
    }

}