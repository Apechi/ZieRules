package com.example.zierules.task

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zierules.task.fragments.ListTugasFragment
import com.example.zierules.task.fragments.ListTugasSayaFragment

class MyViewPagerTask
    (fm: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return ListTugasSayaFragment()
            1 -> return  ListTugasFragment()
            else -> return ListTugasSayaFragment()
        }
    }
}