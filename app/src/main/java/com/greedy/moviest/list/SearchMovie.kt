package com.greedy.moviest.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.moviest.databinding.SearchMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class SearchMovie : Fragment() {

    lateinit var binding: SearchMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = SearchMovieBinding.inflate(inflater, container, false)

        val adapter = RecyclerAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kobis.or.kr/kobisopenapi/webservice/rest/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.btnSearch.setOnClickListener {

            val searchService = retrofit.create(SearchService::class.java)
            searchService.movies("3aa3d5652ba43f83ebdc57e68148b192", 1, 50, "${binding.editSearch.text}")
                .enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                        var movieResponse = response.body() as MovieResponse
                        adapter.movieList = movieResponse.movieListResult.movieList
                        Log.d("movieResponse", movieResponse.toString())
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("retrofit", "통신 실패")
                    }

                })

        }

        return binding.root

    }
}

interface SearchService {
    @GET("searchMovieList.json")
    fun movies(
        @Query("key") key:String?,
        @Query("curPage") curPage:Int?,
        @Query("itemPerPage") itemPerPage:Int?,
        @Query("movieNm") movieNm:String?
    ): Call<MovieResponse>
}