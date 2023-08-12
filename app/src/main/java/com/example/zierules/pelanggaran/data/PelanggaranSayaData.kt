package com.example.zierules.pelanggaran.data


data class PelanggaranSayaData(
    val totalPoint: Int,
    val dataViolation: ArrayList<DataViolation>
)

data class DataViolation(
    val id: Int,
    val name: String,
    val point: Int,
    val teacher: String,
    val description: String
)

