package com.example.zierules.prestasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zierules.R
import com.example.zierules.databinding.ActivityPelanggaranBinding
import com.example.zierules.databinding.ActivityPrestasiBinding
import com.example.zierules.pelanggaran.MyViewPagerPelanggaranAdapter
import com.google.android.material.tabs.TabLayoutMediator

class PrestasiActivity : AppCompatActivity() {

    lateinit var binding: ActivityPrestasiBinding
    var tabTitle = arrayOf("Prestasi Saya", "List Prestasi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrestasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pager = binding.viewPager
        var tl = binding.tabLayout

        pager.adapter = MyViewPagerPrestasiActivity(supportFragmentManager, lifecycle)

        TabLayoutMediator(tl, pager){
                tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }
}