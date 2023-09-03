package com.andrej.busleft.features.route_details.model.remote

data class RemoteRouteData(
    val id: String? = "",
    val name: String? = ""
)

data class RemoteStopsData(
    val id: String? = "",
    val coord: RemoteCoordsData? = null
)

data class RemoteCoordsData(
    val lat: Double? = null,
    val lng: Double? = null
)

data class RemoteStudentData(
    val id: String? = "",
    val name: String? = "",
    val grade: String? = ""
)