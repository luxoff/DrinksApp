package com.appsflow.drinksapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.DrinkListItemBinding
import com.appsflow.drinksapp.model.Drink
import com.squareup.picasso.Picasso

class DrinksListAdapter(dataSet: List<Drink>) :
    RecyclerView.Adapter<DrinksListAdapter.ViewHolder>() {

    private val drinks = dataSet

    class ViewHolder(val binding: DrinkListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DrinkListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink = drinks[position]

        holder.binding.tvTitle.text = drink.strDrink
        Picasso.get()
            .load(drink.strDrinkThumb)
            .placeholder(R.drawable.ic_drink_placeholder)
            .error(R.drawable.ic_error_placeholder)
            .resize(86, 86)
            .centerCrop()
            .into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int {
        return drinks.size
    }

}


