package com.greedy.moviest.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.moviest.databinding.ActivityResultBinding

import java.util.*

import android.annotation.SuppressLint

import android.os.Build

import androidx.annotation.RequiresApi
import com.greedy.moviest.MainActivity
import com.greedy.moviest.list.SearchActivity
import com.greedy.moviest.movieList.MovieActivity
import com.greedy.moviest.movieList.MovieActivity1
import com.greedy.moviest.weather.WeatherActivity


class ResultActivity : AppCompatActivity() {



    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater)}

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        /* 로그인한 계정의 email 화면 출력 */
        binding.currentUser.text = "${auth.currentUser?.email}"

        /* 로그아웃 버튼 클릭 이벤트 */
        binding.btnLogout.setOnClickListener{
            /* 로그아웃 후 로그인 화면으로 갈 때 activity stack을 비우고 이동 */
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

            /* firebase 서버에도 로그아웃 요청 */
            auth.signOut()

        }

        /* 날씨 조회 버튼 클릭시 화면 이동 */
        binding.btnWeather.setOnClickListener{
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        /* 일별박스오피스 버튼 클릭시 화면 이동 */
        binding.btnDailyBoxOffice.setOnClickListener{
            startActivity(Intent(this, MovieActivity::class.java))
        }

        /* 주간박스오피스 버튼 클릭시 화면 이동 */
        binding.btnWeeklyBoxOffice.setOnClickListener{
            startActivity(Intent(this, MovieActivity1::class.java))
        }

        /* 영화검색 버튼 클릭시 화면 이동 */
        binding.btnSearchMv.setOnClickListener{
            startActivity(Intent(this, SearchActivity::class.java))
        }



    }




}