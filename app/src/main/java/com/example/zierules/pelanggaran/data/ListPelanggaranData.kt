package com.example.zierules.pelanggaran.data

data class ListPelanggaranData(
    val dataViolation: ArrayList<ListDataViolation>

)

data class ListDataViolation(
    val id: Int,
    val name: String,
    val point: Int,
)
