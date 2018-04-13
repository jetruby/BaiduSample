package com.example.user.baidusample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final static int ZOOM = 15;
    private final static int Z_INDEX = 7;

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private GeoCoder mGeocoder;
    private PoiSearch mPoiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.map_view);
        initMapStatus(new LatLng(39.97923, 116.357428));
//        addMarker(new LatLng(39.97923, 116.357428));
//        addLine();
//        addPolyline();
//        addText();
    }

    private void initMapStatus(LatLng latLng) {
        mBaiduMap = mMapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(ZOOM);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .zIndex(Z_INDEX)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_black_24dp))
                .draggable(false);
        Marker marker = (Marker) (mBaiduMap.addOverlay(markerOptions));
        marker.setDraggable(false);

    }

    private void addLine() {
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);

        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points);
        Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        mPolyline.setDottedLine(true);
    }

    private void addPolyline() {
        LatLng pt1 = new LatLng(39.93923, 116.357428);
        LatLng pt2 = new LatLng(39.91923, 116.327428);
        LatLng pt3 = new LatLng(39.89923, 116.347428);
        LatLng pt4 = new LatLng(39.89923, 116.367428);
        LatLng pt5 = new LatLng(39.91923, 116.387428);
        List<LatLng> pts = new ArrayList<LatLng>();
        pts.add(pt1);
        pts.add(pt2);
        pts.add(pt3);
        pts.add(pt4);
        pts.add(pt5);

        OverlayOptions polygonOption = new PolygonOptions()
                .points(pts)
                .stroke(new Stroke(5, 0xAA00FF00))
                .fillColor(0xAAFFFF00);

        mBaiduMap.addOverlay(polygonOption);
    }

    private void addText() {
        LatLng llText = new LatLng(39.86923, 116.397428);
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(24)
                .fontColor(0xFFFF00FF)
                .text("YOUR TEXT MARKER")
                .rotate(-30)
                .position(llText);
        mBaiduMap.addOverlay(textOption);
    }


    private void initGeoCode() {
        mGeocoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
                    Toast.makeText(getApplicationContext(), "No result retrieved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Result " + geoCodeResult.getLocation(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
                    Toast.makeText(getApplicationContext(), "No result retrieved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Result " + reverseGeoCodeResult.getAddress(), Toast.LENGTH_SHORT).show();
            }
        };

        mGeocoder.setOnGetGeoCodeResultListener(listener);
        mGeocoder.geocode(new GeoCodeOption().city("北京").address("海淀区上地十街10号"));

//        mGeocoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(40.05703033345938, 116.3084202915042)));
    }

    private void destroyGeoCode() {
        mGeocoder.destroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        initGeoCode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        destroyGeoCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

}