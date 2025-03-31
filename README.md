
# OSM Multi Point Map

OSM based map to display multi pinpoint 

add in your build.gradle

	dependencies {
	        implementation 'com.github.samseptiano:osm-multi-point-map:TAG'
	}
 
add in your XML file

     <com.samuelseptiano.multipointmap.CustomMapView
        android:id="@+id/map"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:zoomLevel="18"
        app:zoomControlVisibility="always"
        app:tileSource="mapnik"
        app:setMultiTouchControl="true"
        app:showRefreshButton="true"/>

 add in your Java/Kotlin file:

 	val customMapView = findViewById<CustomMapView>(R.id.map)

 	//list of pinpoints
	customMapView.listGeoPoint = listOf(
            GeoPointData(GeoPoint(40.758896, -73.985130), "Times Square, NY", 200.0, true), // Times Square
            GeoPointData(GeoPoint(40.748817, -73.985428), "Empire State Building, NY"), // Empire State Building
	)

	customMapView.showMap()

        
Properties:
Attribute | Type | Values
--- | --- | ---
ZoomLevel | float | 
setPinPointCenter | boolean | true, false
setMultiTouchControl | boolean | true, false
ZoomshowRefreshButtonLevel | boolean | true, false
zoomControlVisibility | string | never, showAndFadeOut, always
tileSource | string | mapnik, usgs, openTopo, hikeBike

Sample Image:

<img width="193" alt="image" src="https://github.com/user-attachments/assets/195d737e-4a13-4388-80e1-86fee840e315" />

