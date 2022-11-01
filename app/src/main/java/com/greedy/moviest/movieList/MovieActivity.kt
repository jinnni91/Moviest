package com.greedy.moviest.movieList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.moviest.R
import com.greedy.moviest.databinding.ActivityMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

class MovieActivity : AppCompatActivity() {


    val binding by lazy { ActivityMovieBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* 어댑터 생성 후 리사이클러뷰의 어댑터로 지정 */
        val adapter = MovieAdapter()
        /* 리사이클러어댑터뷰의 속성으로 지정을 해준다. */
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/")
            // Gson을 이용해 JSON을 자동으로 data class에 binding 하기 위해 사용한다.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /* 레트로핏 객체를 생성한다. 요청을 위한 인터페이스의 class정보를 전달한다. */
        val movieService = retrofit.create(MovieService::class.java)

        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        var TimeToDate = calendar.time
        var formatter = SimpleDateFormat("yyyyMMdd")
        //formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        var finalResultDate = formatter.format(TimeToDate)


        movieService.movieRepos(resources.getString(R.string.api_key), finalResultDate).enqueue(object :
            Callback<MovieRepository> {
            override fun onResponse(call: Call<MovieRepository>, response: Response<MovieRepository>) {
                Log.d("movie", response.body().toString())
                val movieRepository = response.body()!!
                adapter.dailyBoxOfficeList = movieRepository.boxOfficeResult.dailyBoxOfficeList.toMutableList()
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieRepository>, t: Throwable) {
                Log.d("retrofit", "통신 실패 ")

            }
        })

    }
}
interface MovieService {

    @GET("searchDailyBoxOfficeList.json")
    fun movieRepos(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): Call<MovieRepository>

}

