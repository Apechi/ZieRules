package com.example.zierules.task.data

data class ListTugasSaya(
    val dataTask: ArrayList<DataTaskSaya>
)

data class DataTaskSaya(
    val id: Int,
    val name: String,
    val teacher: String,
    val `class`: String,
    val date: String,
    val description: String,
)
