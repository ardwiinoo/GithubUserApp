package com.ardwiinoo.githubuserapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardwiinoo.githubuserapp.api.ApiConfig
import com.ardwiinoo.githubuserapp.data.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    fun findUser(input: String) {
        _isLoadingDetail.value = true
        val client = ApiConfig.getApiService().getUserDetails(input)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoadingDetail.value = false
                if(response.isSuccessful && response.body() != null) {
                    _detailUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoadingDetail.value = false
                t.message?.let { Log.d("failure", it) }
            }

        })
    }
}