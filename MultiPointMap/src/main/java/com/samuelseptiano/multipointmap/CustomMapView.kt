package com.samuelseptiano.multipointmap

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

class CustomMapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val mapView: MapView by lazy {
        findViewById(R.id.osmmap)
    }

    private val btnRefresh: ImageView by lazy {
        findViewById(R.id.ib_refresh)
    }

    var listGeoPoint: List<GeoPointData>? = null
    private var setMultiTouchControl = true
    private var zoomControlVisibility = CustomZoomButtonsController.Visibility.ALWAYS
    private var defaultZoomLevel = 19.0
    private var setPinpointCenter = true
    private var setTileSource = TileSourceFactory.MAPNIK
    private var showRefreshButton = false

    init {
        // Inflate layout for the map view
        inflate(context, R.layout.custom_map_view, this) // Inflate XML layout for preview

        btnRefresh.setOnClickListener {
            setCenter(
                listGeoPoint?.first()?.geoPoint?.latitude ?: 0.0,
                listGeoPoint?.first()?.geoPoint?.longitude ?: 0.0,
                defaultZoomLevel
            )
        }

        // Read attributes from XML
        attrs?.let { readAttrFromXML(it) }



        // Initialize osmdroid configuration
        org.osmdroid.config.Configuration.getInstance().userAgentValue = context.packageName
        mapView.setTileSource(setTileSource)
        mapView.setMultiTouchControls(setMultiTouchControl)
        mapView.zoomController.setVisibility(zoomControlVisibility)

    }

    fun showMap(){
        setCenter(
            listGeoPoint?.first()?.geoPoint?.latitude ?: 0.0,
            listGeoPoint?.first()?.geoPoint?.longitude ?: 0.0,
            defaultZoomLevel
        )

        listGeoPoint?.map {
            addMarker(it.geoPoint.latitude, it.geoPoint.longitude, it.title)
            if(it.showRadius){
                drawCircle(it.geoPoint, it.radius)
            }
        }
    }

    fun readAttrFromXML(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMapView)
        defaultZoomLevel =
            typedArray.getFloat(R.styleable.CustomMapView_zoomLevel, 19.0f).toDouble()
        val zoomVisibilityInt =
            typedArray.getInt(R.styleable.CustomMapView_zoomControlVisibility, 2)
        val tileSourceInt = typedArray.getInt(R.styleable.CustomMapView_tileSource, 0)

        setMultiTouchControl =
            typedArray.getBoolean(R.styleable.CustomMapView_setMultiTouchControl, true)
        setPinpointCenter = typedArray.getBoolean(R.styleable.CustomMapView_setPinPointCenter, true)
        showRefreshButton =
            typedArray.getBoolean(R.styleable.CustomMapView_showRefreshButton, false)

        btnRefresh.visibility = if (showRefreshButton) VISIBLE else GONE


        zoomControlVisibility = when (zoomVisibilityInt) {
            0 -> CustomZoomButtonsController.Visibility.NEVER
            1 -> CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT
            else -> CustomZoomButtonsController.Visibility.ALWAYS
        }

        setTileSource = when (tileSourceInt) {
            1 -> TileSourceFactory.USGS_TOPO
            2 -> TileSourceFactory.OpenTopo
            3 -> TileSourceFactory.HIKEBIKEMAP
            else -> TileSourceFactory.MAPNIK
        }

        typedArray.recycle()
    }

    private fun setCenter(latitude: Double, longitude: Double, zoomLevel: Double = 15.0) {
        mapView.controller.setZoom(zoomLevel)
        mapView.controller.setCenter(GeoPoint(latitude, longitude))
    }

    private fun addMarker(latitude: Double, longitude: Double, title: String? = null) {
        val marker = Marker(mapView)
        marker.position = GeoPoint(latitude, longitude)
        marker.icon = resources.getDrawable(R.drawable.ic_pin_outline, null)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title

        mapView.overlays.add(marker)
        mapView.invalidate()
    }

    private fun drawCircle(center: GeoPoint, radiusInMeters: Double) {
        val circle = Polygon().apply {
            fillColor = Color.argb(50, 0, 0, 255) // Semi-transparent blue
            strokeColor = Color.BLUE
            strokeWidth = 3f
            isClickable = false // Allow touch events to pass through
            points = Polygon.pointsAsCircle(center, radiusInMeters) // Generates the circle points
        }
        mapView.overlays.add(0,circle)
        mapView.invalidate() // Refresh the map
    }
}
