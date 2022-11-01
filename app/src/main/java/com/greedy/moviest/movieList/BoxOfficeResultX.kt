package com.greedy.moviest.movieList

data class BoxOfficeResultX(
    val boxofficeType: String,
    val showRange: String,
    val weeklyBoxOfficeList: List<WeeklyBoxOffice>,
    val yearWeekTime: String
)