package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityAdd3Binding
import com.example.myapplication.models.IngrediantsAdapter
import com.example.myapplication.models.RecipeViewModel
import com.example.myapplication.models.RecipeViewModelFactory
import com.example.myapplication.models.StepsAdapter
import java.lang.Exception

class Add3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add3)


        val recipeID = intent.getStringExtra("ADD_ID")
        val recipeName = intent.getStringExtra("ADD_NAME")
        val binding = ActivityAdd3Binding.inflate(layoutInflater)
        supportActionBar?.title = recipeID + ": " + recipeName
        val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(
            applicationContext
        ) }
        binding.lifecycleOwner = this

        val adapter = StepsAdapter(StepsAdapter.ClickListener {
            if (recipeID != null) {
                viewModel.deleteStep(it, recipeID.toLong())
            }
        })

        binding.recyclerView2.adapter = adapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.steps.observe(this) {
            adapter.submitList(it)
        }
        if (recipeID != null) {
            viewModel.getAllSteps(recipeID.toLong())
        }
        binding.button5.setOnClickListener{

            try {
                val text1 = binding.editTextTextMultiLine.text.toString()
                val text2 = binding.editTextTextPersonName2.text.toString().toDouble()
                val text3 = binding.editTextTextPersonName3.text.toString().toInt()

                if (recipeID != null ) {
                    viewModel.addSteps(text1,text2,text3,recipeID.toLong())
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, " A critical error has happend!, Please check all input fields ", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button6.setOnClickListener{ view ->
            if(intent.getStringExtra("ADD_EXTRA") == "0"){
                val intent = Intent(this, MainActivity::class.java).apply {}
                startActivity(intent)
            } else {
                val desc = intent.getStringExtra("ADD_DESC")
                val type = intent.getStringExtra("ADD_TYPE")

                val intent = Intent(this, MainActivity3::class.java).apply {}
                intent.putExtra("ADD_ID", recipeID);
                intent.putExtra("ADD_NAME", recipeName);
                intent.putExtra("ADD_DESC", desc);
                intent.putExtra("ADD_TYPE", type);
                startActivity(intent)
            }
        }

        val view = binding.root
        setContentView(view)
    }
}