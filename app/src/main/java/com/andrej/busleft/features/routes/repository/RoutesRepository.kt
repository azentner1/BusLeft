package com.andrej.busleft.features.routes.repository

import com.andrej.busleft.base.model.ErrorEntity
import com.andrej.busleft.features.routes.mapper.RoutesMapper
import com.andrej.busleft.base.model.DataResult
import com.andrej.busleft.base.api.ApiService
import com.andrej.busleft.features.route_details.model.domain.Routes
import com.andrej.busleft.features.routes.model.remote.RoutesResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutesRepository @Inject constructor(
    private val apiService: ApiService,
    private val routesMapper: RoutesMapper,
) {

    suspend fun fetchRoutes() = flow {
        val response = apiService.fetchRoutes()
        val responseData = handleRoutesResponse(response)
        emit(responseData)
    }.catch {
        emit(DataResult.Error(ErrorEntity.Network))
    }

    private fun handleRoutesResponse(response: Response<RoutesResponse>): DataResult<Routes> {
        return when {
            response.isSuccessful && response.body() != null -> {
                val domainData = routesMapper.mapFromEntity(response)
                DataResult.Success(domainData)
            }
            else -> {
                DataResult.Error(ErrorEntity.Unknown)
            }
        }
    }
}



