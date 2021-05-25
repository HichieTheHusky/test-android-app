package com.example.myapplication.models

import androidx.room.*

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: Recipe): Long

    @Query("SELECT * FROM Recipe ")
    suspend fun getAll(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE Type=:type ")
    suspend fun getAllSpec(type: String): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE recipeID=:id LIMIT 1")
    suspend fun getLast(id : String): Recipe

    @Delete
    suspend fun DeleteRecipe(recipe: Recipe)

    @Insert
    suspend fun insertIngrediant(ingredient: Ingredients)

    @Insert
    suspend fun insertStep(step: Steps)

    @Query("SELECT * FROM Ingredients WHERE recipeCreatorId=:id ")
    suspend fun getIngrediants(id : Long): List<Ingredients>

    @Query("SELECT * FROM Steps WHERE recipeCreatorId=:id ORDER BY spot ASC")
    suspend fun getSteps(id : Long): List<Steps>

    @Delete
    suspend fun DeleteIngr(ingr: Ingredients)

    @Delete
    suspend fun DeleteStep(step: Steps)


}