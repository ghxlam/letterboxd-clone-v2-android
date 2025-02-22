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

class AiringTodayTVShowsFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_airing_today_tv_shows_list, container, false)
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

        client[
            "https://api.themoviedb.org/3/tv/airing_today",
            params,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    progressBar.hide()

                    val resultsJSONArray = json.jsonObject.getJSONArray("results")
                    val tvShowsRawJSON: String = resultsJSONArray.toString()

                    val gson = Gson()
                    val arrayTVShowType = object : TypeToken<List<TVShow>>() {}.type
                    val models: List<TVShow> = gson.fromJson(tvShowsRawJSON, arrayTVShowType)
                    recyclerView.adapter = AiringTodayTVShowsRecyclerViewAdapter(models, this@AiringTodayTVShowsFragment)

                    Log.d("AiringTodayTVShowsFrag", "Response successful")
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    progressBar.hide()
                    t?.message?.let {
                        Log.e("AiringTodayTVShowsFrag", errorResponse)
                    }
                }
            }
        ]
    }

    override fun onItemClick(item: TVShow) {
        Toast.makeText(context, "Selected TV Show: " + item.title, Toast.LENGTH_LONG).show()
    }
}
