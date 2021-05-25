package com.example.myapplication.models;

import android.R.id.message
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch


class RecipeViewModel(context: Context) : ViewModel() {
        private val _insert = MutableLiveData<Long>()
        val insert : LiveData<Long>
                get() = _insert

        private val _ingredients = MutableLiveData<List<Ingredients>>()
        val ingredients : LiveData<List<Ingredients>>
                get() = _ingredients

        private val _steps = MutableLiveData<List<Steps>>()
        val steps : LiveData<List<Steps>>
                get() = _steps

        private val _recipes = MutableLiveData<List<Recipe>>()
        val recipes : LiveData<List<Recipe>>
                get() = _recipes

        val database = Room.databaseBuilder(context, RecipeDatabase::class.java,"recipes").build()

        init{
                getAllRecipeSpec("BreakFest")
        }

        fun getAllRecipe() {
                viewModelScope.launch {
                        _recipes.postValue(database.RecipeDao().getAll())
                }
        }

        fun getAllRecipeSpec(type : String) {
                viewModelScope.launch {
                        _recipes.postValue(database.RecipeDao().getAllSpec(type))
                }
        }

        fun getAllIngrediants(id: Long) {
                viewModelScope.launch {
                        _ingredients.postValue(database.RecipeDao().getIngrediants(id))
                }
        }

        fun getAllSteps(id: Long) {
                viewModelScope.launch {
                        _steps.postValue(database.RecipeDao().getSteps(id))
                }
        }

        
        fun addRecipe(Name: String, Description: String, Type: String): Long? {
                val recipe = Recipe(0, Name, Description, Type)
                viewModelScope.launch {
                        _insert.postValue(database.RecipeDao().insertRecipe(recipe))
                        getAllRecipe()
                }
                return insert.value
        }

        fun addIngrediants(Name: String, Ammount: String, ID: Long) {
                val ingredient = Ingredients(0, ID, Name, Ammount)
                viewModelScope.launch {
                        database.RecipeDao().insertIngrediant(ingredient)
                        getAllIngrediants(ID)
                }
        }

        fun addSteps(step: String, Ammount: Double, Spot: Int, ID: Long) {
                val step = Steps(0, ID, step, Ammount, Spot)
                viewModelScope.launch {
                        database.RecipeDao().insertStep(step)
                        getAllSteps(ID)
                }
        }

        fun deleteRecipe(recipe: Recipe) {
                viewModelScope.launch {
                        database.RecipeDao().DeleteRecipe(recipe)
                        getAllRecipe()
                }
        }

        fun deleteIngr(ingr: Ingredients, ID: Long) {
                viewModelScope.launch {
                        database.RecipeDao().DeleteIngr(ingr)
                        getAllIngrediants(ID)
                }
        }

        fun deleteStep(step: Steps, ID: Long) {
                viewModelScope.launch {
                        database.RecipeDao().DeleteStep(step)
                        getAllSteps(ID)
                }
        }
}
