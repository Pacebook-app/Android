package com.example.pacebook.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pacebook.R
import com.example.pacebook.databinding.ActivityMainBinding
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient("d2nb10oq0w")

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


    }
}