package com.example.datacollector;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class GPS_info extends Activity{

	public void writeFileData(String fileName, String message) {
		try {
			FileOutputStream fout = openFileOutput(fileName, MODE_APPEND);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			//Toast.makeText(this, "GPS module is normal", Toast.LENGTH_SHORT)
			//		.show();
			getLocation();
			return;
		}
		// Toast.makeText(this, "Please open GPS!", Toast.LENGTH_SHORT).show();
		//Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		//startActivityForResult(intent, 0);
	}

	private void getLocation() {
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		saveToLocalFile(location);
		locationManager.requestLocationUpdates(provider, 2 * 1000, 10,
				locationListener);
	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			if (location != null) {
				saveToLocalFile(location);
			}
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private void saveToLocalFile(Location location) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String strDate = dateFormat.format(d);
		if (location != null) {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			double altitude = location.getAltitude();
			writeFileData("location.txt", latitude+"|"+longitude + "|" + altitude+"|"+strDate+"\n");
		} else {
			writeFileData("location.txt", "no location available"+"\n");
		}
	}
}
