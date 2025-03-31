package com.samuelseptiano.multipointmap

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.util.GeoPoint

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        getList()
    }

    private fun getList() {
        val customMapView = findViewById<CustomMapView>(R.id.map)

        customMapView.listGeoPoint = listOf(// Statue of Liberty
            GeoPointData(GeoPoint(40.758896, -73.985130), "Times Square, NY", 200.0, true), // Times Square
            GeoPointData(GeoPoint(40.748817, -73.985428), "Empire State Building, NY"), // Empire State Building
        )

        // Set center to the first location
        customMapView.showMap()

    }
}