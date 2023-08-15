package com.example.zierules.task.data

import android.content.IntentSender.OnFinished

data class  ListTugasSaya(
    val dataTask: ArrayList<DataTaskSaya>
)

data class DataTaskSaya(
    val finished: Boolean,
    val id: Int,
    val name: String,
    val `class`: String,
    val description: String,
    val result : ResultTaskSaya
)

data class ResultTaskSaya(
    val id: Int,
    val teacher: String,
    val date: String,
    val description: String
)
