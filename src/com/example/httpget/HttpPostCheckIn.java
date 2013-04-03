package com.example.httpget;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Time;
import android.widget.TextView;
import android.widget.Toast;

public class HttpPostCheckIn extends Activity {

	TextView httpStuff;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
			StrictMode.setThreadPolicy(policy);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.httppostcheckin);
			httpStuff = (TextView) findViewById(R.id.tvHttpP);
			
			PostCheckInMethod test = new PostCheckInMethod();
			
			String returned;
			Time time = new Time();
			time.setToNow();
			//Toast.makeText(getApplicationContext(), time.year+"-"+time.month+"-"+time.monthDay+" "+time.hour+":"+time.minute+":"+time.second, Toast.LENGTH_LONG).show();
						
			try {
				returned = test.postInternetData();
				httpStuff.setText(returned);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
