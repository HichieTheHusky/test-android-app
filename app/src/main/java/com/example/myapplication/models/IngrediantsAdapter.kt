package com.example.myapplication.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemIngrediantBinding
class IngrediantsAdapter(val clickListener: ClickListener) : ListAdapter<Ingredients, IngrediantsAdapter.ViewHolder>(DiffCallback()){

    class ViewHolder(val binding: ItemIngrediantBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(ingrediant: Ingredients, clickListener: ClickListener) {
            binding.ingrediant = ingrediant
            binding.clickListener = clickListener
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Ingredients>() {
        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem.ingredientID == newItem.ingredientID
        }

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener(val clickListener: (ingr: Ingredients) -> Unit) {
        fun onClick(ingr: Ingredients) {
            clickListener(ingr)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngrediantsAdapter.ViewHolder {
        return ViewHolder(
                ItemIngrediantBinding.inflate(LayoutInflater.from(parent.context)
                )
        )
    }

    override fun onBindViewHolder(holder: IngrediantsAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}