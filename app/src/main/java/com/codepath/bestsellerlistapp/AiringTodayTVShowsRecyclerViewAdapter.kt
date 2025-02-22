package com.codepath.bestsellerlistapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.bestsellerlistapp.R.id
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class AiringTodayTVShowsRecyclerViewAdapter(
    private val tvShows: List<TVShow>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<AiringTodayTVShowsRecyclerViewAdapter.TVShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tv_show_item, parent, false) // Reuse the layout for TV shows
        return TVShowViewHolder(view)
    }

    inner class TVShowViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: TVShow? = null
        val mTVShowTitle: TextView = mView.findViewById<View>(id.tv_show_title) as TextView
        val mTVShowDescription: TextView = mView.findViewById<View>(id.tv_show_description) as TextView
        val mTVShowImage: ImageView = mView.findViewById<View>(id.tv_show_image) as ImageView

        override fun toString(): String {
            return mTVShowTitle.toString() + " '" + mTVShowDescription.text + "'"
        }
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = tvShows[position]

        holder.mItem = tvShow
        holder.mTVShowTitle.text = tvShow.title
        holder.mTVShowDescription.text = tvShow.description

        val posterUrl = "https://image.tmdb.org/t/p/w500/" + tvShow.posterPath
        val roundCorner = RequestOptions().transform(RoundedCorners(10))

        Glide.with(holder.mView)
            .load(posterUrl)
            .apply(roundCorner)
            .centerInside()
            .into(holder.mTVShowImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { tvShow ->
                val intent = Intent(holder.mView.context, TVShowDetailsActivity::class.java)
                intent.putExtra("TITLE", tvShow.title)
                intent.putExtra("DESCRIPTION", tvShow.description)
                intent.putExtra("POSTER_PATH", tvShow.posterPath)
                intent.putExtra("RELEASE_DATE", tvShow.releaseDate)
                intent.putExtra("RATING", tvShow.rating)
                intent.putExtra("LANGUAGE", tvShow.language)
                holder.mView.context.startActivity(intent)
                mListener?.onItemClick(tvShow)
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
}
