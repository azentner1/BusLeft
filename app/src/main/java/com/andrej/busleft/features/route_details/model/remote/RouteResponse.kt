package com.andrej.busleft.features.route_details.model.remote

data class RouteByIdResponse(
    val id: String? = "",
    val name: String? = "",
    val stops: List<RemoteStopsData>? = emptyList(),
    val students: List<RemoteStudentData>? = emptyList(),
    val type: String? = ""
)