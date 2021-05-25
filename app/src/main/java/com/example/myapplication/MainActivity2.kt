package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMain2Binding
import com.example.myapplication.models.IngrediantsAdapter
import com.example.myapplication.models.RecipeViewModel
import com.example.myapplication.models.RecipeViewModelFactory
import java.util.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipeID = intent.getStringExtra("ADD_ID")
        val name = intent.getStringExtra("ADD_NAME")
        val desc = intent.getStringExtra("ADD_DESC")
        val type = intent.getStringExtra("ADD_TYPE")
        val binding = ActivityMain2Binding.inflate(layoutInflater)

        supportActionBar?.title = name

        binding.RecipeName.text = name
        binding.description.text = desc
        binding.description2.text = type

        binding.lifecycleOwner = this

        val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(
            applicationContext
        ) }
        val channelId = "My_Channel_ID"
        createNotificationChannel(channelId)

        val adapter = IngrediantsAdapter(IngrediantsAdapter.ClickListener {

            val notificationBuilder = NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Reminder to buy ")
                .setContentText(it.Name + " " + it.amount)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)){
                notify(Calendar.getInstance().timeInMillis.toInt(), notificationBuilder.build())
            }
        })

        binding.recyclerView3.adapter = adapter
        binding.recyclerView3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.ingredients.observe(this) {
            adapter.submitList(it)
        }

        if (recipeID != null) {
            viewModel.getAllIngrediants(recipeID.toLong())
        }

        binding.button7.setOnClickListener {
            if (recipeID != null) {
                val intent = Intent(this, Add2Activity::class.java).apply {}
                intent.putExtra("ADD_ID", recipeID)
                intent.putExtra("ADD_NAME", name);
                intent.putExtra("ADD_DESC", desc);
                intent.putExtra("ADD_TYPE", type);
                intent.putExtra("ADD_EXTRA", "1");
                startActivity(intent)
            };
        }

        binding.button8.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java).apply {}
            intent.putExtra("ADD_ID", recipeID)
            intent.putExtra("ADD_NAME", name);
            startActivity(intent)
        }
        val view = binding.root
        setContentView(view)
    }
    private fun createNotificationChannel(channelId:String) {
        // Create the NotificationChannel, but only on API 26+ (Android 8.0) because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "My Channel"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId,name,importance)
            channel.apply {
                description = channelDescription
            }

            // Finally register the channel with system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}