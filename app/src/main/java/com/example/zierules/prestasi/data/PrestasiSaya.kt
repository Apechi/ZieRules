package com.example.zierules.prestasi.data

data class PrestasiSaya(
    var totalPoint: Int,
    var dataAchievments: ArrayList<DataAchievement>
)

data class DataAchievement(
    var id: Int,
    var name: String,
    var date: String,
    var point: Int,
    var teacher: String,
    var description: String
)
