package com.andrej.busleft.features.route_details.repository

import android.content.Context
import com.andrej.busleft.R
import com.andrej.busleft.base.api.ApiService
import com.andrej.busleft.base.model.DataResult
import com.andrej.busleft.base.model.ErrorEntity
import com.andrej.busleft.features.route_details.mapper.RouteMapper
import com.andrej.busleft.features.route_details.model.remote.RouteByIdResponse
import com.andrej.busleft.features.routes.model.domain.Route
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteDetailsRepository @Inject constructor(
    private val apiService: ApiService,
    private val routeMapper: RouteMapper,
    @ApplicationContext val context: Context
) {

    suspend fun fetchRouteById(routeId: String) = flow {
        val response = apiService.fetchRouteById(routeId)
        val responseData = handleRouteByIdResponse(response)
        emit(responseData)
    }.catch {
        emit(DataResult.Error(ErrorEntity.Network))
    }

    private fun handleRouteByIdResponse(response: Response<RouteByIdResponse>): DataResult<Route> {
        return when {
            response.isSuccessful && response.body() != null -> {
                val domainData = routeMapper.mapFromEntity(response)
                DataResult.Success(domainData)
            }
            else -> {
                DataResult.Error(ErrorEntity.Unknown)
            }
        }
    }


    fun fetchOptimizedRoute(route: Route) = flow {
        val routeOptions = RouteOptions.builder()
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .coordinatesList(route.stops.map {
                Point.fromLngLat(it.coord.lng, it.coord.lat)
            })
            .build()
        val client = MapboxDirections.builder()
            .accessToken(context.getString(R.string.mapbox_access_token))
            .routeOptions(routeOptions)
            .build()

        val response = client.executeCall()
        val dataResult = when {
            response.isSuccessful && response.body() != null -> {
                val routeJson = response.body()?.routes()?.get(0)?.geometry()
                DataResult.Success(routeJson)
            }
            else -> {
                DataResult.Error(ErrorEntity.Unknown)
            }
        }
        emit(dataResult)
    }
}



