package com.andrej.busleft.features.routes.mapper

import com.andrej.busleft.base.mapper.EntityMapper
import com.andrej.busleft.features.route_details.model.domain.Routes
import com.andrej.busleft.features.routes.model.domain.Route
import com.andrej.busleft.features.routes.model.remote.RoutesResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutesMapper @Inject constructor() : EntityMapper<Response<RoutesResponse>, Routes> {

    override fun mapFromEntity(entity: Response<RoutesResponse>): Routes {
        return Routes(
            routeList = entity.body()?.data?.map {
                Route(
                    id = it.id ?: "",
                    name = it.name ?: ""
                )
            } ?: emptyList()
        )
    }

    override fun mapToEntity(model: Routes): Response<RoutesResponse> {
        TODO("Not needed")
    }
}