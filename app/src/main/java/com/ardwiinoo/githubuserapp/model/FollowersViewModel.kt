package com.ardwiinoo.githubuserapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardwiinoo.githubuserapp.api.ApiConfig
import com.ardwiinoo.githubuserapp.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    private val _followersUser = MutableLiveData<List<User>>()
    val followersUser: LiveData<List<User>> = _followersUser

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    fun getFollowersUser(input: String?) {
        _isLoadingFollower.value = true

        val client = ApiConfig.getApiService().getUserFollowers(input)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFollower.value = false
                if(response.isSuccessful && response.body() != null) {
                    _followersUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollower.value = false
                t.message?.let { Log.d("failure", it) }
            }
        })
    }
}