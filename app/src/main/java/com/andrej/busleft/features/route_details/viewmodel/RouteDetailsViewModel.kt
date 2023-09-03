package com.andrej.busleft.features.route_details.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrej.busleft.R
import com.andrej.busleft.base.model.DataResult
import com.andrej.busleft.features.route_details.repository.RouteDetailsRepository
import com.andrej.busleft.features.routes.model.domain.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val repository: RouteDetailsRepository
) : ViewModel() {

    val viewState = MutableStateFlow<RouteDetailsEvent>(RouteDetailsEvent.Loading)

    fun fetchRouteById(routeId: String) {
        viewModelScope.launch {
            repository.fetchRouteById(routeId).collect {
                handleFetchedRoute(it)
            }
        }
    }

    fun fetchOptimizedRoute(route: Route) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchOptimizedRoute(route).collect {
                handleFetchedOptimizedRoute(it)
            }
        }
    }

    private fun handleFetchedRoute(dataResult: DataResult<Route>) {
        viewState.value = when (dataResult) {
            is DataResult.Success -> {
                RouteDetailsEvent.OnRouteData(
                    route = dataResult.data
                )
            }

            is DataResult.Error -> {
                RouteDetailsEvent.Error(R.string.network_error)
            }

            else -> {
                RouteDetailsEvent.Loading
            }
        }
    }

    private fun handleFetchedOptimizedRoute(dataResult: DataResult<String?>) {
        when (dataResult) {
            is DataResult.Success -> {
                viewState.value = RouteDetailsEvent.OnOptimizedRouteData(
                    optimizedRouteJson = dataResult.data
                )
            }

            is DataResult.Error -> {
                viewState.value = RouteDetailsEvent.Error(
                    stringResId = R.string.unable_to_fetch_route_data,
                    isToast = true
                )
            }

            else -> {}
        }
    }
}

sealed class RouteDetailsEvent {
    object Loading : RouteDetailsEvent()
    data class OnRouteData(val route: Route) : RouteDetailsEvent()
    data class OnOptimizedRouteData(val optimizedRouteJson: String? = null) : RouteDetailsEvent()
    data class Error(@StringRes val stringResId: Int, val isToast: Boolean = false) :
        RouteDetailsEvent()
}
