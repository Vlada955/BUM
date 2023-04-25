package com.bumtraining.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TrainingDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TRAINING_NAME = "training_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_details)
    }
}