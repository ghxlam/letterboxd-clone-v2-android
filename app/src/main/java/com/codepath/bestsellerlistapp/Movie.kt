package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("overview")
    var description: String? = null
}