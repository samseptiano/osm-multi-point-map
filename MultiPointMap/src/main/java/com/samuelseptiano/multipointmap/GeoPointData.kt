package com.samuelseptiano.multipointmap

import org.osmdroid.util.GeoPoint

/**
 * Created by samuel.septiano on 27/03/2025.
 */
data class GeoPointData (
    var geoPoint: GeoPoint,
    var title:String = "",
    var radius: Double = 0.0,
    var showRadius: Boolean = false,

)
