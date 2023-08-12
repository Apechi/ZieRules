package com.example.zierules.prestasi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.manager.Lifecycle
import com.example.zierules.prestasi.fragments.ListPrestasiFragment
import com.example.zierules.prestasi.fragments.PrestasiSayaFragment

class MyViewPagerPrestasiActivity
    (fm: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return  PrestasiSayaFragment()
            1 -> return  ListPrestasiFragment()
            else -> return  PrestasiSayaFragment()
        }
    }

}