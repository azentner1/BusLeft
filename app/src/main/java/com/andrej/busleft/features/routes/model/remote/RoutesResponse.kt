package com.andrej.busleft.features.routes.model.remote

import com.andrej.busleft.features.route_details.model.remote.RemoteRouteData

data class RoutesResponse(
    val data: List<RemoteRouteData>? = emptyList()
)