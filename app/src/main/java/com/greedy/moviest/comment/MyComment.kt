package com.greedy.moviest.comment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.moviest.databinding.MyCommentBinding


class MyComment : Fragment() {

    lateinit var baseContext: Context
    lateinit var helper: SqliteHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = MyCommentBinding.inflate(inflater, container, false)

        helper = SqliteHelper(baseContext, "comment", 1)

        val adapter = CommentAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper.selectCommentList())

        binding.recyclerView3.adapter = adapter
        binding.recyclerView3.layoutManager = LinearLayoutManager(baseContext)

        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseContext = context
    }
}