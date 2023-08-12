package com.example.zierules.prestasi.data

data class ListPrestasi(
   val achievement: ArrayList<DataAchievementSaya>
)

data class DataAchievementSaya(
    val id: Int,
    val name: String,
    val point: Int,
)
