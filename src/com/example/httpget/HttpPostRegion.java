package com.example.httpget;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class HttpPostRegion extends Activity{
	TextView httpStuff;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
			StrictMode.setThreadPolicy(policy);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.httppostregion);
			httpStuff = (TextView) findViewById(R.id.tvHttpP);
			
			PostRegionMethod postRegionMethod = new PostRegionMethod();
			String returned;
			
			try {
				returned = postRegionMethod.postInternetData();
				httpStuff.setText(returned);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
