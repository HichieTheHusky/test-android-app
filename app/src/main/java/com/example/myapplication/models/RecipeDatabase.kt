package com.example.myapplication.models

import androidx.room.Database
import androidx.room.RoomDatabase

//@Database(entities = {arrayOf(Recipe::class), arrayOf(Ingredients::class), arrayOf(Steps::class)}, version = 1)
@Database(entities = [Recipe::class, Ingredients::class, Steps::class], version = 1)
    abstract class RecipeDatabase : RoomDatabase()  {
        abstract fun RecipeDao(): RecipeDao
    }