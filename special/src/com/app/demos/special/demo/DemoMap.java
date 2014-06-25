package com.app.demos.special.demo;

import com.app.demos.special.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;

public class DemoMap extends MapActivity {
	
	MapView map;
	MapController mapController;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_map);
		
		map =(MapView) this.findViewById(R.id.demo_map_view);
//		map.setTraffic(true);		// 交通地图
//		map.setSatellite(true);		// 卫星地图
		map.setStreetView(true);	// 街景地图
		map.setEnabled(true);
		map.setClickable(true);
		map.setBuiltInZoomControls(true);
		
		mapController = map.getController();
		mapController.animateTo(new GeoPoint((int)(31.237141*1000000), (int)(121.501622*1000000)));
		mapController.setZoom(15);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}