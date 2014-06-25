package com.app.demos.special.demo;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import com.app.demos.special.R;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class DemoLbs extends Activity implements LocationListener {
	
	private LocationManager lm;
	
	private String provider = LocationManager.GPS_PROVIDER;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_lbs);
		
		initLocation();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (lm != null) {
			lm.requestLocationUpdates(provider, 3000, 0, this);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (lm != null) {
			lm.removeUpdates(this);
		}
		
	}
	
	private void initLocation() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (provider == null) {
			provider = lm.getBestProvider(criteria, true);
		}
		Location location = lm.getLastKnownLocation(provider);
		updateLocation(location);
	}
	
	private void updateLocation(Location location) {
		String result = "";
		if (location != null) {
			// get position
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			DecimalFormat df = new DecimalFormat("#.000000");
			String latStr = df.format(lat);
			String lngStr = df.format(lng);
			result = "Î³¶È£º" + latStr + "\n¾­¶È£º" + lngStr;
			// get address
			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addressList = gc.getFromLocation(lat, lng, 1);
				StringBuilder sb = new StringBuilder();
				if (addressList.size() > 0) {
					Address address = addressList.get(0);
					int addressMaxIndex = address.getMaxAddressLineIndex();
					for (int i = 0; i < addressMaxIndex; i++) {
						sb.append(address.getAddressLine(i)).append("\n");
					}
					sb.append(address.getCountryName()).append("\n");
					sb.append(address.getLocality()).append("\n");
					result += "\n" + sb.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			result = "Can not find address";
		}
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		updateLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}
}