package com.example.httpget;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

import android.widget.TextView;



public class HttpPostRegister extends Activity {
TextView httpStuff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
			StrictMode.setThreadPolicy(policy);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.httppostregister);
			httpStuff = (TextView) findViewById(R.id.tvHttpP);
			
			PostRegisterMethod test = new PostRegisterMethod();
			String returned;
				
			try {
				
				returned = test.postInternetData();
				httpStuff.setText(returned);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

	
	
	
}
