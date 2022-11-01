package com.greedy.moviest.movieList

data class BoxOfficeResult(
    val boxofficeType: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>,
    val showRange: String
)