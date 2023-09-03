package com.andrej.busleft.features.routes.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrej.busleft.R
import com.andrej.busleft.base.model.DataResult
import com.andrej.busleft.features.route_details.model.domain.Routes
import com.andrej.busleft.features.routes.repository.RoutesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteListViewModel @Inject constructor(
    private val routesRepository: RoutesRepository
) : ViewModel() {

    val viewState = MutableStateFlow<RouteListEvent>(RouteListEvent.Loading)

    init {
        fetchRoutes()
    }

    fun fetchRoutes() {
        viewModelScope.launch {
            routesRepository.fetchRoutes().collect {
                handleFetchedRoutes(it)
            }
        }
    }

    private fun handleFetchedRoutes(dataResult: DataResult<Routes>) {
        viewState.value = when (dataResult) {
            is DataResult.Loading -> RouteListEvent.Loading
            is DataResult.Success -> {
                RouteListEvent.OnRouteListData(
                    routes = dataResult.data
                )
            }

            is DataResult.Error -> {
                RouteListEvent.Error(
                    stringResId = R.string.network_error
                )
            }
        }
    }
}

sealed class RouteListEvent {
    object Loading : RouteListEvent()
    data class OnRouteListData(val routes: Routes) : RouteListEvent()
    data class Error(@StringRes val stringResId: Int, val isToast: Boolean = false) :
        RouteListEvent()
}