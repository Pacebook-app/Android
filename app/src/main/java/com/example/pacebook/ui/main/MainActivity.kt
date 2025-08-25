package com.example.pacebook.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pacebook.R
import com.example.pacebook.databinding.ActivityMainBinding
import com.example.pacebook.R
import com.example.pacebook.ui.main.RunningmateFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, RunningmateFragment())
                .commit()
        }




        //맨 처음 화면을 보여줄 때, HomeFragment Default
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, RunningInitialFragment())
                .commit()
        }

        binding.mainBnv.setOnClickListener { item ->
            when (item.itemId) {
                R.id.
            }
        }
    }
}