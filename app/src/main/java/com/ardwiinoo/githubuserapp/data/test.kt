package com.ardwiinoo.githubuserapp.data

import com.google.gson.annotations.SerializedName

data class Test(

	@SerializedName("total_count")
	val totalCount: Int,

	@SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@SerializedName("items")
	val items: List<User?>
)
