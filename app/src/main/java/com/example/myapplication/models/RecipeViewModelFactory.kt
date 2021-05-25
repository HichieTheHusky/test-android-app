package com.example.myapplication.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class RecipeViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return  RecipeViewModel(context) as T
        }
        throw IllegalArgumentException()
    }
}