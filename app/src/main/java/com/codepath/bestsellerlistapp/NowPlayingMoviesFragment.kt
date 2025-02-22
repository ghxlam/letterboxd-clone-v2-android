package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class NowPlayingMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        // The Movie Database now_playing endpoint
        client[
            "https://api.themoviedb.org/3/movie/now_playing",
            params,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    progressBar.hide()

                    val resultsJSONArray = json.jsonObject.getJSONArray("results")
                    val moviesRawJSON: String = resultsJSONArray.toString()

                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                    val models: List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                    recyclerView.adapter = NowPlayingMoviesRecyclerViewAdapter(models, this@NowPlayingMoviesFragment)

                    Log.d("NowPlayingMoviesFrag", "response successful")
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    progressBar.hide()
                    t?.message?.let {
                        Log.e("NowPlayingMoviesFrag", errorResponse)
                    }
                }
            }
        ]
    }

    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "Selected Movie: " + item.title, Toast.LENGTH_LONG).show()
    }
}