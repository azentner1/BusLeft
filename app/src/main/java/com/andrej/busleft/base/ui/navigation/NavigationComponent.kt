package com.andrej.busleft.base.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andrej.busleft.features.route_details.ui.RouteDetailsScreen
import com.andrej.busleft.features.routes.ui.RouteListScreen

@Composable
fun NavigationComponent(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationDirections.routeList.destination
    ) {
        composable(route = NavigationDirections.routeList.destination) {
            RouteListScreen(onNavigateToDetails = { routeId ->
                navHostController.navigate("${NavigationDirections.routeDetails.destination}/$routeId")
            })
        }
        composable(
            route = NavigationDirections.routeDetails.destination + DETAILS_ARG_PLACEHOLDER,
            arguments = listOf(navArgument(DETAILS_ARG) { NavType.StringType })
        ) {
            val routeId = it.arguments?.getString(DETAILS_ARG)
            RouteDetailsScreen(routeId = routeId, onNavigateToRouteList = {
                navHostController.navigate(NavigationDirections.routeList.destination)
            })
        }
    }
}

private const val DETAILS_ARG = "routeId"
private const val DETAILS_ARG_PLACEHOLDER = "/{routeId}"