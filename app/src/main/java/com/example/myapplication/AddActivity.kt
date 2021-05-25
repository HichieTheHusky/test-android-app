package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAddBinding
import android.view.View.OnClickListener;
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.models.RecipeViewModel
import com.example.myapplication.models.RecipeViewModelFactory

class AddActivity : AppCompatActivity()   {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        supportActionBar?.title = "Adding new recipe"

        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.Category_Array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.button.setOnClickListener{ view ->
            val name = binding.RecipeName.text.toString();
            val description = binding.description.text.toString();
            val type = binding.spinner.selectedItem.toString();

            if(name.trim().isNotEmpty() && description.trim().isNotEmpty())
            {
                val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(
                    applicationContext
                ) }

                viewModel.addRecipe(name, description, type)

                viewModel.insert.observe(this, {
                    val intent = Intent(this, Add2Activity::class.java).apply {}
                    intent.putExtra("ADD_ID", "$it");
                    intent.putExtra("ADD_NAME", name);
                    intent.putExtra("ADD_EXTRA", "0");
                    startActivity(intent)
                })
            } else {
                Toast.makeText(applicationContext, "Please enter text fields! ", Toast.LENGTH_SHORT).show()
            }
        }
        val view = binding.root
        setContentView(view)
    }
}

