package com.example.zierules.task

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zierules.task.fragments.ListTugasSayaFragment

class MyViewPagerTask
    (fm: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return ListTugasSayaFragment()
            else -> return ListTugasSayaFragment()
        }
    }
}