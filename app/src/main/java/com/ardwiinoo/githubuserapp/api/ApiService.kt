package com.ardwiinoo.githubuserapp.api

import com.ardwiinoo.githubuserapp.data.DetailUserResponse
import com.ardwiinoo.githubuserapp.data.GithubResponse
import com.ardwiinoo.githubuserapp.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowings(
        @Path("username") username: String?
    ): Call<List<User>>
}