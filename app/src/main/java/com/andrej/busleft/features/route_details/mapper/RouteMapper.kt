package com.andrej.busleft.features.route_details.mapper

import com.andrej.busleft.base.mapper.EntityMapper
import com.andrej.busleft.features.route_details.model.remote.RouteByIdResponse
import com.andrej.busleft.features.routes.model.domain.Route
import retrofit2.Response
import javax.inject.Inject

class RouteMapper @Inject constructor() : EntityMapper<Response<RouteByIdResponse>, Route> {

    override fun mapFromEntity(entity: Response<RouteByIdResponse>): Route {
        val route = entity.body()
        return Route(
            id = route?.id ?: "",
            name = route?.name ?: "",
            stops = route?.stops?.map { stop ->
                Route.Stop(
                    id = stop.id ?: "",
                    coord = Route.Coords(
                        lat = stop.coord?.lat ?: 0.0,
                        lng = stop.coord?.lng ?: 0.0
                    )
                )
            } ?: emptyList(),
            students = route?.students?.map { student ->
                Route.Student(
                    id = student.id ?: "",
                    name = student.name ?: "",
                    grade = student.grade ?: ""
                )
            } ?: emptyList(),
            type = route?.type ?: ""
        )
    }

    override fun mapToEntity(model: Route): Response<RouteByIdResponse> {
        TODO("Not needed")
    }
}