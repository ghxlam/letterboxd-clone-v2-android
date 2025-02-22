package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class TVShowDetailsActivity : AppCompatActivity() {

    private lateinit var tvShowTitle: TextView
    private lateinit var tvShowDescription: TextView
    private lateinit var tvShowImage: ImageView
    private lateinit var tvShowReleaseDate: TextView
    private lateinit var tvShowRating: TextView
    private lateinit var tvShowLanguage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_details)

        tvShowTitle = findViewById(R.id.tv_show_title)
        tvShowDescription = findViewById(R.id.tv_show_description)
        tvShowImage = findViewById(R.id.tv_show_image)
        tvShowReleaseDate = findViewById(R.id.tv_show_release_date)
        tvShowRating = findViewById(R.id.tv_show_rating)
        tvShowLanguage = findViewById(R.id.tv_show_language)

        val title = intent.getStringExtra("TITLE")
        val description = intent.getStringExtra("DESCRIPTION")
        val posterPath = intent.getStringExtra("POSTER_PATH")
        val releaseDate = intent.getStringExtra("RELEASE_DATE")
        val rating = intent.getStringExtra("RATING")
        val language = intent.getStringExtra("LANGUAGE")

        tvShowTitle.text = title
        tvShowDescription.text = description
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/$posterPath")
            .into(tvShowImage)
        tvShowReleaseDate.text = "Release Date: $releaseDate"
        tvShowRating.text = "Rating: $rating"
        tvShowLanguage.text = "Language: $language"
    }
}
