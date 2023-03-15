package com.ardwiinoo.githubuserapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ardwiinoo.githubuserapp.R
import com.ardwiinoo.githubuserapp.adapter.SectionsPagerAdapter
import com.ardwiinoo.githubuserapp.data.DetailUserResponse
import com.ardwiinoo.githubuserapp.databinding.ActivityDetailBinding
import com.ardwiinoo.githubuserapp.model.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {

        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get intent
        val username = intent.getStringExtra(EXTRA_USERNAME)

        if (username != null) {
            detailViewModel.findUser(username)
        }

        // pantau isi data user
        detailViewModel.detailUser.observe(this) { item ->
            setDataUser(item)
        }

        detailViewModel.isLoadingDetail.observe(this) {
            showLoading(it)
        }

        // ubah title dan back btn
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // send username to adapter
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.INVISIBLE
        }
    }

    private fun setDataUser(userItem: DetailUserResponse) {
        binding.apply {
            Glide.with(applicationContext)
                .load(userItem.avatarUrl)
                .error(R.drawable.baseline_person_24)
                .into(imgProfileUser)
            tvUserFullName.text = userItem.name
            tvUsername.text = userItem.login
            tvUserFollowersNumber.text = userItem.followers.toString()
            tvUserFollowingsNumber.text = userItem.following.toString()
        }
    }

}