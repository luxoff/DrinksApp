package com.appsflow.drinksapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.appsflow.drinksapp.R
import com.appsflow.drinksapp.databinding.DrinkListItemBinding
import com.appsflow.drinksapp.model.Drink
import com.appsflow.drinksapp.utils.CircleTransformation
import com.appsflow.drinksapp.view.DetailsFragmentDirections
import com.squareup.picasso.Picasso

class DrinksListAdapter(
    dataSet: List<Drink>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<DrinksListAdapter.ViewHolder>() {

    private val drinks = dataSet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DrinkListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val circleTransformation = CircleTransformation()
        val drink = drinks[position]

        holder.binding.tvTitle.text = drink.strDrink
        Picasso.get()
            .load(drink.strDrinkThumb)
            .placeholder(R.drawable.ic_drink_placeholder)
            .error(R.drawable.ic_error_placeholder)
            .resize(85, 85)
            .centerCrop()
            .transform(circleTransformation)
            .into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int {
        return drinks.size
    }

    inner class ViewHolder(val binding: DrinkListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {

        }
    }
}


