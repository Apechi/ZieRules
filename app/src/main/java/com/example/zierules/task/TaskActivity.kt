package com.example.zierules.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zierules.R
import com.example.zierules.databinding.ActivityTaskBinding
import com.google.android.material.tabs.TabLayoutMediator

class TaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskBinding
    var tabTitle = arrayOf("Tugas Saya", "List Tugas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var pager = binding.viewPager

        var tabLayout = binding.tabLayout

        pager.adapter = MyViewPagerTask(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, pager) {
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()
    }
}