package com.example.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMain4Binding
import com.example.myapplication.models.RecipeViewModel
import com.example.myapplication.models.RecipeViewModelFactory
import com.example.myapplication.models.StepsAdapter
import java.util.concurrent.TimeUnit


class MainActivity3 : AppCompatActivity() {

    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)


        val recipeID = intent.getStringExtra("ADD_ID")
        val name = intent.getStringExtra("ADD_NAME")
        val binding = ActivityMain4Binding.inflate(layoutInflater)

        supportActionBar?.title = name

        binding.lifecycleOwner = this

        val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(
            applicationContext
        ) }



        val adapter = StepsAdapter(StepsAdapter.ClickListener {
            if (recipeID != null) {
                if(it.amount != 0.0)
                {
                    var time = it.amount.toString().split('.')
                    var militime = TimeUnit.MINUTES.toMillis(time[0].toLong()) + TimeUnit.SECONDS.toMillis(time[1].toLong())

                    if(this::timer.isInitialized)
                    {
                        timer.cancel()
                    }

                    timer = object : CountDownTimer(militime, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            if (millisUntilFinished / 1000 < 100)
                                supportActionBar?.title ="Timer : " + "seconds remaining: " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                            else
                                supportActionBar?.title ="Timer : " + "minutes remaining: " + TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) + " : " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                        }
                        override fun onFinish() {

                            val player = MediaPlayer.create(applicationContext, Settings.System.DEFAULT_ALARM_ALERT_URI)
                            player.start()
                            object : CountDownTimer(10000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    supportActionBar?.title = "$name      Alarm will stop after : "+ TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                }
                                override fun onFinish() {
                                    supportActionBar?.title = "$name      Timer is done"
                                    player.stop()
                                }
                            }.start()
                        }
                    }.start()

                }
            }
        })

        binding.recyclerView4.adapter = adapter
        binding.recyclerView4.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.steps.observe(this) {
            adapter.submitList(it)
        }

        if (recipeID != null) {
            viewModel.getAllSteps(recipeID.toLong())
        }

        binding.floatingActionButton5.setOnClickListener {
            if (recipeID != null) {
                val intent = Intent(this, MainActivity::class.java).apply {}
                startActivity(intent)
            };
        }
        binding.floatingActionButton6.setOnClickListener {
            if (recipeID != null) {
                val desc = intent.getStringExtra("ADD_DESC")
                val type = intent.getStringExtra("ADD_TYPE")

                val intent = Intent(this, Add3Activity::class.java).apply {}
                intent.putExtra("ADD_ID", recipeID)
                intent.putExtra("ADD_NAME", name);
                intent.putExtra("ADD_DESC", desc);
                intent.putExtra("ADD_TYPE", type);
                intent.putExtra("ADD_EXTRA", "1");
                startActivity(intent)
            };
        }
        val view = binding.root
        setContentView(view)
    }
}