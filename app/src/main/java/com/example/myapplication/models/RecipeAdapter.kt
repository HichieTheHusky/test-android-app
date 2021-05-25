package com.example.myapplication.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRecipeBinding

class RecipeAdapter (val clickListener: ClickListener) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback()){

    class ViewHolder(val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe, clickListener: ClickListener) {
            binding.recipe = recipe
            binding.clickListener = clickListener
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.recipeID == newItem.recipeID
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener(val clickListener: (recipe: Recipe) -> Unit) {
        fun onClick(recipe: Recipe) {
            clickListener(recipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.ViewHolder {
        return ViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


}