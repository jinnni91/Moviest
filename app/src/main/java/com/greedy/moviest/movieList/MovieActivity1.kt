package com.greedy.moviest.movieList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.moviest.R
import com.greedy.moviest.databinding.ActivityMovie1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

class MovieActivity1 : AppCompatActivity() {

    val binding by lazy { ActivityMovie1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = MovieAdapter1()

        binding.recyclerview1.adapter = adapter
        binding.recyclerview1.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/")
            // Gson을 이용해 JSON을 자동으로 data class에 binding 하기 위해 사용한다.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /* 레트로핏 객체를 생성한다. 요청을 위한 인터페이스의 class정보를 전달한다. */
        val movieService1 = retrofit.create(MovieService1::class.java)

        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7) //변경하고 싶은 원하는 날짜 수를 넣어 준다.
        var TimeToDate = calendar.time
        var formatter = SimpleDateFormat("yyyyMMdd") //날짜의 모양을 원하는 대로 변경 해 준다.
        //formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        var finalResultDate = formatter.format(TimeToDate)


        movieService1.movieRepos1(resources.getString(R.string.api_key), finalResultDate).enqueue(object :
            Callback<MovieRepository1> {
            override fun onResponse(call: Call<MovieRepository1>, response: Response<MovieRepository1>) {
                Log.d("movie", response.body().toString())
                val movieRepository1 = response.body()!!
                adapter.weeklyBoxOfficeList = movieRepository1.boxOfficeResult.weeklyBoxOfficeList.toMutableList()
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieRepository1>, t: Throwable) {
                Log.d("retrofit", "통신 실패 ")

            }
        })

    }
}
interface MovieService1 {

    @GET("searchWeeklyBoxOfficeList.json")
    fun movieRepos1(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): Call<MovieRepository1>

}