package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

class TVShow {
    @SerializedName("name")
    var title: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("overview")
    var description: String? = null

    @SerializedName("first_air_date")
    var releaseDate: String? = null

    @SerializedName("vote_average")
    var rating: String? = null

    @SerializedName("original_language")
    var language: String? = null
}