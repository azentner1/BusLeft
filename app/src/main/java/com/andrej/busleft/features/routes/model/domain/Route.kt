package com.andrej.busleft.features.routes.model.domain

data class Route(
    val id: String,
    val name: String,
    val stops: List<Stop> = emptyList(),
    val students: List<Student> = emptyList(),
    val type: String = ""
) {

    data class Stop(
        val id: String,
        val coord: Coords
    )

    data class Coords(
        val lat: Double,
        val lng: Double
    )

    data class Student(
        val id: String,
        val name: String,
        val grade: String
    )
}