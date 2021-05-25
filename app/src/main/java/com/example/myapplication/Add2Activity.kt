package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityAdd2Binding
import com.example.myapplication.models.IngrediantsAdapter
import com.example.myapplication.models.RecipeViewModel
import com.example.myapplication.models.RecipeViewModelFactory

class Add2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add2)

        val recipeID = intent.getStringExtra("ADD_ID")
        val recipeName = intent.getStringExtra("ADD_NAME")

        val binding = ActivityAdd2Binding.inflate(layoutInflater)
        val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(
                applicationContext
        ) }

        binding.lifecycleOwner = this

        val adapter = IngrediantsAdapter(IngrediantsAdapter.ClickListener {
            if (recipeID != null) {
                viewModel.deleteIngr(it, recipeID.toLong())
            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.ingredients.observe(this) {
            adapter.submitList(it)
        }

        if (recipeID != null) {
            viewModel.getAllIngrediants(recipeID.toLong())
        }

        supportActionBar?.title = "$recipeID : $recipeName"
        binding.button3.setOnClickListener {
            val text1 = binding.editTextTextPersonName4.text.toString()
            val text2 = binding.editTextTextPersonName5.text.toString()
            if (recipeID != null && text1 != "" && text2 != "" ) {
                viewModel.addIngrediants(text1, text2, recipeID.toLong())
            } else {
                Toast.makeText(applicationContext, "Please check all input fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button4.setOnClickListener{ view ->
            if(intent.getStringExtra("ADD_EXTRA") == "0"){
                val intent = Intent(this, Add3Activity::class.java).apply {}
                intent.putExtra("ADD_ID", recipeID);
                intent.putExtra("ADD_NAME", recipeName);
                startActivity(intent)
            } else {
                val desc = intent.getStringExtra("ADD_DESC")
                val type = intent.getStringExtra("ADD_TYPE")

                val intent = Intent(this, MainActivity2::class.java).apply {}
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