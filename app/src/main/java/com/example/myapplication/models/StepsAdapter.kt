package com.example.myapplication.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemStepBinding

class StepsAdapter (val clickListener: ClickListener) : ListAdapter<Steps, StepsAdapter.ViewHolder>(DiffCallback()){

    class ViewHolder(val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: Steps, clickListener: ClickListener) {
            binding.step = step
            binding.clickListener = clickListener
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Steps>() {
        override fun areItemsTheSame(oldItem: Steps, newItem: Steps): Boolean {
            return oldItem.stepID == newItem.stepID
        }

        override fun areContentsTheSame(oldItem: Steps, newItem: Steps): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener(val clickListener: (step: Steps) -> Unit) {
        fun onClick(step: Steps) {
            clickListener(step)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsAdapter.ViewHolder {
        return ViewHolder(
            ItemStepBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: StepsAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}