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

class FollowingsViewModel : ViewModel() {

    companion object {
        private const val TAG = "FollowingsViewModel"
    }

    private val _followingsUser = MutableLiveData<List<User>>()
    val followingsUser: LiveData<List<User>> = _followingsUser

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    fun getFollowingsUser(input: String?) {
        _isLoadingFollowing.value = true

        val client = ApiConfig.getApiService().getUserFollowings(input)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFollowing.value = false
                if(response.isSuccessful && response.body() != null) {
                    _followingsUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollowing.value = false
                t.message?.let { Log.d("failure", it) }
            }
        })
    }
}