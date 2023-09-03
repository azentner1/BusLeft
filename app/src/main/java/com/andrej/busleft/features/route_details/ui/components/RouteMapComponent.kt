package com.andrej.busleft.features.route_details.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.andrej.busleft.R
import com.andrej.busleft.features.routes.model.domain.Route
import com.google.gson.JsonElement
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.LineString
import com.mapbox.geojson.MultiPoint
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.observable.eventdata.StyleLoadedEventData
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfConversion
import com.mapbox.turf.TurfMeasurement
import kotlin.math.log

private fun drawRoute(style: Style, json: String) {
    when {
        style.getSource(LINE_ID) == null -> {
            style.addSource(
                GeoJsonSource.Builder(LINE_ID)
                    .lineMetrics(true)
                    .build()
            )
        }

        else -> {
            val source: GeoJsonSource? = style.getSourceAs(LINE_ID)
            source?.let {
                val lineString = LineString.fromPolyline(json, Constants.PRECISION_6)
                source.feature(Feature.fromGeometry(lineString))
            }
        }
    }
}

private fun addLineLayer(style: Style) {
    if (!style.styleLayerExists(LINE_LAYER)) {
        style.addLayer(LineLayer(LINE_LAYER, LINE_ID))
    }
}

private fun drawStops(mapView: MapView, stops: List<Route.Stop>, stopIcon: Bitmap) {
    val annotationApi = mapView.annotations
    val pointAnnotationManager = annotationApi.createPointAnnotationManager()
    pointAnnotationManager.let {
        pointAnnotationManager.deleteAll()
        val pointAnnotationOptions = stops.mapNotNull { stop ->
            when {
                stop.coord.lat == 0.0 || stop.coord.lng == 0.0 -> {
                    null
                }

                else -> {
                    PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(stop.coord.lng, stop.coord.lat))
                        .withIconImage(stopIcon)
                }
            }
        }
        pointAnnotationManager.create(pointAnnotationOptions)
        zoomFit(stops, mapView.getMapboxMap())
    }
}

@Composable
fun MapComponent(
    stops: List<Route.Stop>,
    optimizedJson: String?
) {
    val context = LocalContext.current
    val marker = remember(context) {
        context.getDrawable(R.drawable.ic_marker_bus_stop_sign)!!.toBitmap()
    }
    val mapView = remember { MapView(context) }
    AndroidView(factory = { mapView })

    mapView.getMapboxMap().loadStyleUri(Style.LIGHT)
    optimizedJson?.let { json ->
        mapView.getMapboxMap().getStyle { style ->
            drawRoute(style, json)
            addLineLayer(style)
        }
    }
    LaunchedEffect(Unit) {
        drawStops(mapView, stops, marker)
    }
}

private fun zoomFit(points: List<Route.Stop>, map: MapboxMap) {
    val lineGeo =
        LineString.fromLngLats(points.map { Point.fromLngLat(it.coord.lng, it.coord.lat) })
    val cameraOptions = map.cameraForGeometry(lineGeo)
    val newCamOptions = CameraOptions.Builder()
        .zoom(DEFAULT_ZOOM_LEVEL)
        .bearing(cameraOptions.bearing)
        .padding(cameraOptions.padding)
        .center(cameraOptions.center)
        .build()
    map.easeTo(newCamOptions)
}

private const val LINE_ID = "LINE_ID"
private const val LINE_LAYER = "line_layer"
private const val DEFAULT_ZOOM_LEVEL = 12.0