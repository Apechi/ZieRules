package com.example.zierules.json

data class StudentData(
    val student: Student,
)

data class Student(
    val name: String,
    val nis: String,
    val gender: String,
    val image: String,
    val `class`: String,
    val role: String,
    val dataViolations: Int,
    val dataAchievements: Int,
    val dataTasks: Int
)