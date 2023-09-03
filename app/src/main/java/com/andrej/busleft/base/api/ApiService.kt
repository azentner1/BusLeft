package com.andrej.busleft.base.api

import com.andrej.busleft.features.route_details.model.remote.RouteByIdResponse
import com.andrej.busleft.features.routes.model.remote.RoutesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("mobile/routes")
    suspend fun fetchRoutes(): Response<RoutesResponse>

    @GET("/mobile/routes/{routeId}")
    suspend fun fetchRouteById(@Path("routeId") routeId: String): Response<RouteByIdResponse>
}
