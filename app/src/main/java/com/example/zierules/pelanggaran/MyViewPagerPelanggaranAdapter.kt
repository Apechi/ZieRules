package com.example.zierules.pelanggaran

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zierules.pelanggaran.fragments.ListPelanggaranFragment
import com.example.zierules.pelanggaran.fragments.PelanggaranSayaFragment

class MyViewPagerPelanggaranAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return PelanggaranSayaFragment()
            1 -> return  ListPelanggaranFragment()
            else -> return  PelanggaranSayaFragment()
        }
    }
}