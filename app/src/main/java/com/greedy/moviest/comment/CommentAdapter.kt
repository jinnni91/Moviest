package com.greedy.moviest.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.moviest.databinding.CommentRecyclerBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.Holder> () {

    var listData = mutableListOf<Comment>()
    var helper: SqliteHelper? = null

    inner class Holder(val binding: CommentRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        var cComment: Comment? = null

        init {
            binding.btnDelete.setOnClickListener {
                helper?.deleteComment(cComment!!)
                listData.remove(cComment)
                notifyDataSetChanged()
            }
        }

        fun setComment(comment: Comment) {
            binding.textCode.text = comment.movieCd
            binding.textTitle.text = comment.title
            binding.textContent.text = comment.content

            cComment = comment
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CommentRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val comment = listData[position]
        holder.setComment(comment)
    }

}