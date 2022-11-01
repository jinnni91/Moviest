package com.greedy.moviest.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.moviest.databinding.ItemRecyclerBinding
import com.greedy.moviest.detail.DetailActivity


class RecyclerAdapter : RecyclerView.Adapter<MovieHolder>() {

    var movieList : List<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {

        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)

    }

    override fun getItemCount(): Int {

        return movieList?.size ?: 0

    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val movie = movieList?.get(position)
        holder.setMovieHolder(movie)

    }

}

class MovieHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var movie: Movie

    init {
        binding.root.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("movieCd", movie.movieCd)
            it.context.startActivity(intent)
        }
    }

    fun setMovieHolder(movie: Movie?) {

        movie?.let {
            binding.textCode.text = movie.movieCd
            binding.textTitle.text = movie.movieNm
            binding.textDate.text = movie.prdtYear
        }

        this.movie = movie!!
    }

}