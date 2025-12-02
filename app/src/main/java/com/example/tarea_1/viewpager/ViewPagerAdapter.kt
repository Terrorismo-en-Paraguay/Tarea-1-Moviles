package com.example.tarea_1.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tarea_1.fragment.FavFragment
import com.example.tarea_1.fragment.ListFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2 // Tenemos dos pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment()
            1 -> FavFragment()
            else -> ListFragment()
        }
    }
}