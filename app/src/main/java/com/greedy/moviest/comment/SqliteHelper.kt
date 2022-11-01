package com.greedy.moviest.comment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SqliteHelper (context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {

        val create = "create table comment (" +
                "movieCd text primary key, " +
                "title text, " +
                "content text " +
                ")"

        db?.execSQL(create)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertComment(comment: Comment) {
        val values = ContentValues()
        values.put("movieCd", comment.movieCd)
        values.put("title", comment.title)
        values.put("content", comment.content)

        val wd = writableDatabase
        wd.insert("comment", null, values)
        wd.close()
    }

    @SuppressLint("Range")
    fun selectComment(movieCd: String): Comment? {
        if(readableDatabase == null) {
            onCreate(readableDatabase)
        }

        val rd = readableDatabase

        val select = "select * from comment where movieCd = ${movieCd}"


        var cursor = rd.rawQuery(select, null)
        var comment: Comment? = null

        if(cursor.moveToNext()) {
            val movieCd = cursor.getString(cursor.getColumnIndex("movieCd"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val content = cursor.getString(cursor.getColumnIndex("content"))

            comment = Comment(movieCd, title, content)
        }

        cursor.close()
        rd.close()

        return comment
    }

    @SuppressLint("Range")
    fun selectCommentList(): MutableList<Comment> {
        if(readableDatabase == null) {
            onCreate(readableDatabase)
        }

        val rd = readableDatabase

        val select = "select * from comment"
        val list = mutableListOf<Comment>()

        var cursor = rd.rawQuery(select, null)

        while(cursor.moveToNext()) {
            val movieCd = cursor.getString(cursor.getColumnIndex("movieCd"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            list.add(Comment(movieCd, title, content))
        }

        cursor.close()
        rd.close()

        return list
    }

    fun deleteComment(comment: Comment) {
        val delete = "delete from comment where movieCd = ${comment.movieCd}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }

}