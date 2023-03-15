package com.ardwiinoo.githubuserapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardwiinoo.githubuserapp.api.ApiConfig
import com.ardwiinoo.githubuserapp.data.GithubResponse
import com.ardwiinoo.githubuserapp.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "arif"
    }

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    val searchQuery = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUsers(USERNAME)
    }

    fun findUsers(input: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(input)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _listUser.value = response.body()?.users as List<User>?
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                t.message?.let { Log.d("failure", it) }
            }
        })
    }
}