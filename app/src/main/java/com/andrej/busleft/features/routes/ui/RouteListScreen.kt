package com.andrej.busleft.features.routes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.andrej.busleft.features.routes.viewmodel.RouteListViewModel
import com.andrej.busleft.base.model.DataResult
import com.andrej.busleft.base.ui.components.ErrorComponent
import com.andrej.busleft.base.ui.components.FullscreenLoadingComponent
import com.andrej.busleft.features.route_details.model.domain.Routes
import com.andrej.busleft.features.routes.ui.components.RouteListComponent
import com.andrej.busleft.features.routes.viewmodel.RouteListEvent

@Composable
fun RouteListScreen(
    viewModel: RouteListViewModel = hiltViewModel(),
    onNavigateToDetails: (routeId: String) -> Unit
) {

    val viewState by viewModel.viewState.collectAsState()

    when (viewState) {
        is RouteListEvent.Loading -> {
            FullscreenLoadingComponent()
        }

        is RouteListEvent.OnRouteListData -> {
            val routes = (viewState as RouteListEvent.OnRouteListData).routes
            RouteListComponent(routes = routes, onNavigateToDetails)
        }

        is RouteListEvent.Error -> {
            ErrorComponent(errorResId = 0, showRetryButton = true, onNetworkRetry = {
                viewModel.fetchRoutes()
            })
        }
    }
}

