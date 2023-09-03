package com.andrej.busleft.features.route_details.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.andrej.busleft.base.ui.components.ErrorComponent
import com.andrej.busleft.base.ui.components.FullscreenLoadingComponent
import com.andrej.busleft.features.route_details.ui.components.RouteDetailsComponent
import com.andrej.busleft.features.route_details.viewmodel.RouteDetailsEvent
import com.andrej.busleft.features.route_details.viewmodel.RouteDetailsViewModel
import com.andrej.busleft.features.routes.model.domain.Route

@Composable
fun RouteDetailsScreen(
    viewModel: RouteDetailsViewModel = hiltViewModel(),
    routeId: String?,
    onNavigateToRouteList: () -> Unit
) {

    val uiState by viewModel.viewState.collectAsState()

    val route = remember { mutableStateOf<Route?>(null) }
    val optimizedJson = remember { mutableStateOf<String?>(null) }

    when (uiState) {
        is RouteDetailsEvent.Loading -> {
            FullscreenLoadingComponent()
        }

        is RouteDetailsEvent.OnRouteData -> {
            val data = (uiState as RouteDetailsEvent.OnRouteData)
            route.value = data.route
            viewModel.fetchOptimizedRoute(route = data.route)
        }

        is RouteDetailsEvent.OnOptimizedRouteData -> {
            optimizedJson.value =
                (uiState as RouteDetailsEvent.OnOptimizedRouteData).optimizedRouteJson
        }

        is RouteDetailsEvent.Error -> {
            val error = uiState as RouteDetailsEvent.Error
            when {
                error.isToast -> {
                    showToast(LocalContext.current)
                }
                else -> {
                    ErrorComponent(showRetryButton = false, errorResId = error.stringResId)
                }
            }
        }
    }

    if (route.value == null && routeId != null) {
        viewModel.fetchRouteById(routeId)
    }
    route.value?.let {
        RouteDetailsComponent(route = it, optimizedJson.value, onNavigateToRouteList)
    }
}


private fun showToast(context: Context) {
    Toast.makeText(context, "This is a Sample Toast", Toast.LENGTH_LONG).show()
}


