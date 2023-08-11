package com.example.zierules.pelanggaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zierules.R
import com.example.zierules.databinding.ActivityPelanggaranBinding
import com.google.android.material.tabs.TabLayoutMediator

class PelanggaranActivity : AppCompatActivity() {

    lateinit var binding: ActivityPelanggaranBinding
    var tabTitle = arrayOf("Pelanggaran Saya", "List Pelanggaran")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPelanggaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pager = binding.viewPager
        var tl = binding.tabLayout

        pager.adapter = MyViewPagerPelanggaranAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()
    }
}