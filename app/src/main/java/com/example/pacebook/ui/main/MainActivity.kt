package com.example.pacebook.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pacebook.R
import com.example.pacebook.databinding.ActivityMainBinding
import com.example.pacebook.ui.community.CommunityFragment
import com.example.pacebook.ui.mypage.MypageFragment
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient("d2nb10oq0w")

        // 첫 화면은 러닝(지도) 화면으로
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, RunningInitialFragment())
                .commit()
            binding.mainBnv.selectedItemId = R.id.runningFragment
        }

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.communityFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, CommunityFragment())
                        .commit()
                    true
                }

                R.id.runningFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, RunningInitialFragment())
                        .commit()
                    true
                }

                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, MypageFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }


    }
}