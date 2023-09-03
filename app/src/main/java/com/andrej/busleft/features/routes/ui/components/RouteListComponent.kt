package com.andrej.busleft.features.routes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrej.busleft.R
import com.andrej.busleft.features.route_details.model.domain.Routes
import com.andrej.busleft.features.routes.model.domain.Route

@Composable
fun RouteListComponent(routes: Routes, onNavigateToDetails: (routeId: String) -> Unit) {
    LazyColumn(modifier = Modifier.testTag(TEST_TAG_ROUTE_LIST), content = {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.routes),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        items(routes.routeList.size) {
            RouteListItemComponent(
                modifier = Modifier.testTag(TEST_TAG_ROUTE_LIST_ITEM.replace(TEST_TAG_ARG_ROUTE_LIST_ITEM, it.toString())),
                route = routes.routeList[it],
                onNavigateToDetails = { routeId -> onNavigateToDetails(routeId) })
        }
    })
}

@Composable
fun RouteListItemComponent(modifier: Modifier, route: Route, onNavigateToDetails: (routeId: String) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onNavigateToDetails(route.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.ic_road_sign_filled),
                contentDescription = "route list icon"
            )
            Text(
                modifier = Modifier.weight(1f),
                text = route.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "route list icon"
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Gray)
                .padding(horizontal = 16.dp)
        )
    }
}

const val TEST_TAG_ROUTE_LIST = "route_list"
const val TEST_TAG_ROUTE_LIST_ITEM = "route_list_x"
const val TEST_TAG_ARG_ROUTE_LIST_ITEM = "x"