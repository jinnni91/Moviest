package com.greedy.moviest.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.moviest.databinding.MovieRecycler1Binding


class MovieAdapter1 : RecyclerView.Adapter<Holder1> () {

    var weeklyBoxOfficeList : MutableList<WeeklyBoxOffice> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder1 {
        val binding = MovieRecycler1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder1(binding)
    }

    override fun onBindViewHolder(holder: Holder1, position: Int) {
        val movieRepo1 = weeklyBoxOfficeList?.get(position)
        holder.setmovieRepo1(movieRepo1)
    }

    override fun getItemCount(): Int {
        return weeklyBoxOfficeList.size
    }


}

class Holder1(val binding: MovieRecycler1Binding) : RecyclerView.ViewHolder(binding.root) {

    fun setmovieRepo1(movieRepo1: WeeklyBoxOffice?) {
        movieRepo1?.let {
            binding.movieNm1.text = movieRepo1.movieNm
            binding.rank1.text = movieRepo1.rank
            binding.openDt1.text = movieRepo1.openDt
            binding.audiAcc1.text = movieRepo1.audiAcc
        }


    }
}