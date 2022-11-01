package com.greedy.moviest.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.moviest.databinding.MovieRecyclerBinding


class MovieAdapter : RecyclerView.Adapter<Holder> () {

    var dailyBoxOfficeList : MutableList<DailyBoxOffice> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MovieRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return dailyBoxOfficeList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movieRepo = dailyBoxOfficeList?.get(position)
        holder.setmovieRepo(movieRepo)
    }
}

class Holder(val binding: MovieRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setmovieRepo(movieRepo: DailyBoxOffice?) {
        movieRepo?.let {
           binding.movieNm.text = movieRepo.movieNm
         binding.rank.text = movieRepo.rank
            binding.openDt.text = movieRepo.openDt
            binding.audiAcc.text = movieRepo.audiAcc
        }
    }



}