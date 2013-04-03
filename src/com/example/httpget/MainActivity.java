package com.example.httpget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the GET button */
    public void getRequest(View view) {
    	
    	Intent intent = new Intent(this, HttpGetExample.class);
    	startActivity(intent);
    	
    }
    
    /** Called when the user clicks the CHECK IN button */
    public void postCheckInRequest(View view){
    	
    	Intent intent = new Intent(this, HttpPostCheckIn.class);
    	startActivity(intent);
    }
    
    public void postRegisterRequest(View view){
    	
    	Intent intent = new Intent(this, HttpPostRegister.class);
    	startActivity(intent);    
    }
    
}
