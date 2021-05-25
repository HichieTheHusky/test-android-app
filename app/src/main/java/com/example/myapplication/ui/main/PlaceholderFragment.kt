package com.example.myapplication.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Add2Activity
import com.example.myapplication.MainActivity2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.models.RecipeAdapter
import com.example.myapplication.models.RecipeViewModel
import com.example.myapplication.models.RecipeViewModelFactory


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible) {
            if (isVisibleToUser) // If we are becoming visible, then...
            {
                val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(requireContext())}

                val number = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
                if (number == 1){
                    Log.e("tag", "test1")
                    viewModel.getAllRecipeSpec("BreakFest")
                } else if (number == 2){
                    Log.d("tag", "test2")
                    viewModel.getAllRecipeSpec("Dinner")
                } else if (number == 3){
                    Log.e("tag", "test3")
                    viewModel.getAllRecipeSpec("Supper")
                } else{
                    Log.e("tag", "test4")
                    viewModel.getAllRecipeSpec("Dessert")
                }
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(requireContext())}

        val adapter = RecipeAdapter(RecipeAdapter.ClickListener {

            val intent = Intent(requireContext(), MainActivity2::class.java).apply {}
            intent.putExtra("ADD_ID", "${it.recipeID}");
            intent.putExtra("ADD_NAME", "${it.Name}");
            intent.putExtra("ADD_DESC", "${it.Description}");
            intent.putExtra("ADD_TYPE", "${it.Type}");
            startActivity(intent)
        })
        binding.recipes.adapter = adapter
        binding.recipes.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        viewModel.recipes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    return binding.root
}

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}