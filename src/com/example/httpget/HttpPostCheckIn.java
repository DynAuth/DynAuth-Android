package com.example.httpget;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Time;
import android.widget.TextView;
import android.widget.Toast;

public class HttpPostCheckIn extends Activity {

	TextView httpStuff;
	double lon;
	double lat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
			StrictMode.setThreadPolicy(policy);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.httppostcheckin);
			httpStuff = (TextView) findViewById(R.id.tvHttpP);
			
			PostCheckInMethod test = new PostCheckInMethod();
			
			String returned;
			String testlat = null;
			String testlon = null;
			Time time = new Time();
			time.setToNow();
			Toast.makeText(getApplicationContext(), time.year+"-"+time.month+"-"+time.monthDay+" "+time.hour+":"+time.minute+":"+time.second, Toast.LENGTH_LONG).show();
			
			
			try {
				
				//Toast.makeText(getApplicationContext(), "LAT:" + latitude, Toast.LENGTH_LONG).show();
				
				//Toast.makeText(getApplicationContext(), "LON:" + longitude, Toast.LENGTH_LONG).show();
				
				//test.latt = testlat;
				//test.longg = testlon;
			
				returned = test.postInternetData();
				httpStuff.setText(returned);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
