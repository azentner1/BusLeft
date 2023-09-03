package com.andrej.busleft.mappers

import com.andrej.busleft.features.route_details.model.domain.Routes
import com.andrej.busleft.features.route_details.model.remote.RemoteRouteData
import com.andrej.busleft.features.routes.mapper.RoutesMapper
import com.andrej.busleft.features.routes.model.remote.RoutesResponse
import org.junit.Test
import retrofit2.Response

class RoutesMapperTest {


    @Test
    fun routesMappingTest() {
        val mapper = RoutesMapper()
        val mockRemoteData = mockRemoteRoutesData()
        val mappedData = mapper.mapFromEntity(mockRemoteData)
        assert(mappedData is Routes)
        assert(mappedData.routeList.first().name == "Mick MC")
        assert(mappedData.routeList.size == 3)
    }

    private fun mockRemoteRoutesData(): Response<RoutesResponse> {
        val routesResponse = RoutesResponse(
            data = mockRemoteRouteData()
        )
        return Response.success(routesResponse)
    }

    private fun mockRemoteRouteData() = listOf(
        RemoteRouteData(
            id = "12345",
            name = "Mick MC",
        ),
        RemoteRouteData(
            id = "54321",
            name = "SpiderPig"
        ),
        RemoteRouteData(
            id = "01293",
            name = "T.B."
        )
    )
}