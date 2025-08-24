package com.example.pacebook.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pacebook.R
import com.example.pacebook.ui.main.RunningmateFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, RunningmateFragment())
                .commit()
        }




    }
}