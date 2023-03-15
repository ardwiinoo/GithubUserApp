package com.ardwiinoo.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ardwiinoo.githubuserapp.view.FollowersFragment
import com.ardwiinoo.githubuserapp.view.FollowingsFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String?) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> {
                fragment = FollowersFragment()
                username?.let {
                    (fragment as FollowersFragment).arguments = Bundle().apply {
                        putString(FollowersFragment.ARG_USERNAME, it)
                    }
                }
            }
            1 -> {
                fragment = FollowingsFragment()
                username?.let {
                    fragment.arguments = Bundle().apply {
                        putString(FollowingsFragment.ARG_USERNAME, it)
                    }
                }
            }
        }

        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
