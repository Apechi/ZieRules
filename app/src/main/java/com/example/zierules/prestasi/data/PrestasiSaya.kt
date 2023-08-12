package com.example.zierules.prestasi.data

data class PrestasiSaya(
    var total_point: Int,
    var dataAchievments: ArrayList<DataAchievement>
)

data class DataAchievement(
    var id: Int,
    var name: String,
    var point: Int,
    var teacher: String,
    var description: String
)
