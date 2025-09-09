package com.example.pacebook.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pacebook.R
import com.example.pacebook.databinding.ActivityMainBinding
import com.example.pacebook.ui.community.CommunityFragment
import com.example.pacebook.ui.mypage.MypageFragment
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragments = mutableMapOf<String, Fragment>()
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient("d2nb10oq0w")

        // Fragment를 미리 생성하고 관리하는 로직
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()

            fragments["running"] = RunningInitialFragment()
            fragments["community"] = CommunityFragment()
            fragments["mypage"] = MypageFragment()

            // 초기 Fragment를 추가하고 나머지는 숨김
            transaction.add(R.id.main_fragment_container, fragments["running"]!!, "running")
            transaction.hide(fragments["community"]!!)
            transaction.hide(fragments["mypage"]!!)
            transaction.commit()
            activeFragment = fragments["running"]
        }

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

    private fun switchFragment(targetFragment: Fragment) {
        if (activeFragment != targetFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            activeFragment?.let { transaction.hide(it) }
            transaction.show(targetFragment)
            transaction.commit()
            activeFragment = targetFragment
        }
    }
}