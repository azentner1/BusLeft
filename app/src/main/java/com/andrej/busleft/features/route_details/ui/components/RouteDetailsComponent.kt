package com.andrej.busleft.features.route_details.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrej.busleft.R
import com.andrej.busleft.features.routes.model.domain.Route

@Composable
fun RouteDetailsComponent(route: Route, optimizedJson: String?, onNavigateToRouteList: () -> Unit) {
    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .testTag(TEST_TAG_ROUTE_DETAILS)) {
            RouteDetailsToolbarComponent(
                route = route,
                onNavigateToRouteList = onNavigateToRouteList
            )
            MapComponent(
                stops = route.stops,
                optimizedJson = optimizedJson
            )
        }
        RouteDetailsInfoComponent(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.BottomCenter),
            route = route
        )
    }
}

@Composable
fun RouteDetailsToolbarComponent(route: Route, onNavigateToRouteList: () -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.clickable {
                onNavigateToRouteList()
            },
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "back button"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = route.name,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RouteDetailsInfoComponent(modifier: Modifier, route: Route) {
    Row(
        modifier = modifier.testTag(TEST_TAG_ROUTE_DETAILS_INFO)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.ic_marker_bus_stop_sign),
                contentDescription = "Number of stops"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = route.stops.size.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.ic_school),
                contentDescription = "Number of students"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = route.students.size.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

const val TEST_TAG_ROUTE_DETAILS = "route_details"
const val TEST_TAG_ROUTE_DETAILS_INFO = "route_details_info"