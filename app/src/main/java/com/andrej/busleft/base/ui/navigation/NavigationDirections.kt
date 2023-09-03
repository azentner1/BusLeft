package com.andrej.busleft.base.ui.navigation

object NavigationDirections {

    val routeList = object : NavigationItem {
        override val destination: String by lazy { ROUTE_LIST }
    }

    val routeDetails = object : NavigationItem {
        override val destination: String by lazy { ROUTE_DETAILS }
    }

    private const val ROUTE_LIST = "route_list"
    private const val ROUTE_DETAILS = "route_details"
}