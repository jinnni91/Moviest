package com.greedy.moviest.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.greedy.moviest.comment.Comment
import com.greedy.moviest.comment.SqliteHelper
import com.greedy.moviest.databinding.ActivityDetailBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class DetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var movieInfo: MovieInfo
    val helper = SqliteHelper(this, "comment", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movieCd = intent.getStringExtra("movieCd")

        val retrofit2 = Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/kobisopenapi/webservice/rest/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val detailService = retrofit2.create(DetailService::class.java)

        detailService.detail("3aa3d5652ba43f83ebdc57e68148b192", movieCd).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                var detailResponse = response.body() as DetailResponse
                Log.d("detailResponse", detailResponse.toString())

                movieInfo = detailResponse.movieInfoResult.movieInfo
                binding.detailTitle.text = movieInfo.movieNm
                binding.detailCode.text = movieInfo.movieCd
                binding.detailGenre.text = movieInfo.genresString
                binding.detailYear.text = movieInfo.prdtYear
                binding.detailDirector.text = movieInfo.directorString

            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("retrofit2", "통신 실패")
            }

        })

        var comment = helper.selectComment(movieCd!!)
        Log.d("content", "${comment}")


        if(comment == null) {
            binding.editReview.visibility = View.VISIBLE
            binding.btnRegist.visibility = View.VISIBLE
        } else {
            binding.textView.text = comment.content
            binding.textView.visibility = View.VISIBLE

            binding.editReview.visibility = View.INVISIBLE
            binding.btnRegist.visibility = View.INVISIBLE
        }

        binding.btnRegist.setOnClickListener {

            if(binding.editReview.text.toString().isNotEmpty()) {
                val comment = Comment(binding.detailCode.text.toString(), binding.detailTitle.text.toString(), binding.editReview.text.toString())
                helper.insertComment(comment)

                binding.textView.text = comment.content
                binding.editReview.visibility = View.INVISIBLE
                binding.btnRegist.visibility = View.INVISIBLE
            }


        }

        binding.btnBack.setOnClickListener {

            finish()

        }

    }

}

interface DetailService {
    @GET("searchMovieInfo.json")
    fun detail(
        @Query("key") key:String?,
        @Query("movieCd") movieCd:String?
    ): Call<DetailResponse>
}