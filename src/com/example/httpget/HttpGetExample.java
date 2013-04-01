package com.example.httpget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class HttpGetExample extends Activity {
	
	TextView httpStuff;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
			StrictMode.setThreadPolicy(policy);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.httpgetex);
			httpStuff = (TextView) findViewById(R.id.tvHttpG);
			GetMethod test = new GetMethod();
			String returned;
			try {
				returned = test.getInternetData();
				httpStuff.setText(returned);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

	
	
	
}
