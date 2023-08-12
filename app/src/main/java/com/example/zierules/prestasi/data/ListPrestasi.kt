package com.example.zierules.prestasi.data

data class ListPrestasi(
   val achievments: ArrayList<DataListPrestasi>
)

data class DataListPrestasi(
    val id: Int,
    val name: String,
    val point: Int,
)
