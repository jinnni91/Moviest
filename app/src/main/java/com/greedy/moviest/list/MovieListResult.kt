package com.greedy.moviest.list

import com.greedy.moviest.list.Movie

data class MovieListResult(
    val movieList: List<Movie>,
    val source: String,
    val totCnt: Int
)