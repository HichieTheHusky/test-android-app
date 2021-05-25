package com.example.myapplication.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val recipeID: Int,
    val Name: String,
    val Description: String,
    val Type: String
)

@Entity
data class Ingredients(
    @PrimaryKey(autoGenerate = true) val ingredientID: Int,
    val recipeCreatorId: Long,
    val Name: String,
    val amount: String
)

@Entity
data class Steps(
    @PrimaryKey(autoGenerate = true) val stepID: Int,
    val recipeCreatorId: Long,
    val step: String,
    val amount: Double,
    val spot: Int
)

data class RecipeWithIngrediants(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeID",
        entityColumn = "recipeCreatorId"
    )
    val ingredients: List<Ingredients>
)

data class RecipeWithSteps(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeID",
        entityColumn = "recipeCreatorId"
    )
    val steps: List<Steps>
)
