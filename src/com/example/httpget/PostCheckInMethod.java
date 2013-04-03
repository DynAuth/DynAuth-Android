package com.example.httpget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.StrictMode;
import android.text.format.Time;



public class PostCheckInMethod extends Activity {
	
	
	String latt;
	String longg; 
	String latitude;
	String longitude;
	
	
	public String postInternetData() throws Exception{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
		StrictMode.setThreadPolicy(policy);
		String data = null;
		URI website = new URI("http://cs5221.oko.io:8000/api/device/check-in");
		HttpClient client = new DefaultHttpClient();
		HttpPost client_post = new HttpPost(website);
		
		GPSTracker gps = new GPSTracker(this);
		if(gps.canGetLocation()){
			
			latitude = String.valueOf(gps.getLatitude());
			longitude = String.valueOf(gps.getLongitude());
			
		}
		else{
		latitude = "1.1";
		longitude = "-1.1";
		}
		Time time = new Time();
		time.setToNow();
		
		
		
		try{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			
		
			//String.valueOf(longitude)
			nameValuePairs.add(new BasicNameValuePair("device_id", "667252485e9c43fea5912ad1f291e787"));
			nameValuePairs.add(new BasicNameValuePair("latitude", latitude ));
			nameValuePairs.add(new BasicNameValuePair("longitude", longitude ));
			nameValuePairs.add(new BasicNameValuePair("time", time.year+"-"+time.month+"-"+time.monthDay+" "+time.hour+":"+time.minute+":"+time.second));
			
			client_post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			//POST THE LIST OF NAME VALUE PAIRS AND RETRIEVE RESPONSE
			HttpResponse response = client.execute(client_post);
			HttpEntity entity = response.getEntity();
			InputStream ins = entity.getContent();
			
			//RETRIEVE THE RESPONSE :D
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
			StringBuilder sb = new StringBuilder();
			
			String line = null;
			try{
				while ((line = reader.readLine()) != null) {
					sb.append((line + "\n"));
				}
				
			}catch (IOException e){
				
				e.printStackTrace();
			
			}finally{
				
				try{
					ins.close();
					
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			data = sb.toString();
			return data;
					
		}catch (ClientProtocolException e){
			
		}catch (IOException e){
			
		}
		return data;
	}	
}
