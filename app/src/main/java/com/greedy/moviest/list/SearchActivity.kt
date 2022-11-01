package com.greedy.moviest.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greedy.moviest.R
import com.greedy.moviest.comment.MyComment
import com.greedy.moviest.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFrag(0)

        binding.btnGoSearch.setOnClickListener {
            setFrag(0)
        }

        binding.btnComments.setOnClickListener {
            setFrag(1)
        }

    }

    private fun setFrag(fragNum : Int) {

        val ft = supportFragmentManager.beginTransaction()

        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.main_frame, SearchMovie()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, MyComment()).commit()
            }
        }

    }
}