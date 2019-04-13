package com.kamer.aviasalestest


import android.graphics.PointF
import com.google.android.gms.maps.model.LatLng

/**
 * Math borrowed from https://github.com/googlemaps/android-maps-utils/blob/master/library/src/com/google/maps/android/projection/SphericalMercatorProjection.java
 */

private const val worldWidth = 1f

fun LatLng.toPoint(): PointF {
    val x = longitude / 360 + .5
    val siny = Math.sin(Math.toRadians(latitude))
    val y = 0.5 * Math.log((1 + siny) / (1 - siny)) / -(2 * Math.PI) + .5

    return PointF(x.toFloat() * worldWidth, y.toFloat() * worldWidth)
}

fun PointF.toLatLng(): LatLng {
    val x = this.x / worldWidth - 0.5
    val lng = x * 360

    val y = .5 - this.y / worldWidth
    val lat = 90 - Math.toDegrees(Math.atan(Math.exp(-y * 2.0 * Math.PI)) * 2)

    return LatLng(lat, lng)
}