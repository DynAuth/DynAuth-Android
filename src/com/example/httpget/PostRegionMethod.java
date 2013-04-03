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

import android.os.StrictMode;
import android.text.format.Time;

public class PostRegionMethod {
	public String postInternetData() throws Exception{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
		StrictMode.setThreadPolicy(policy);
		String data = null;
		URI website = new URI("http://cs5221.oko.io:8000/api/device/check-in");
		
		HttpClient client = new DefaultHttpClient();
		HttpPost client_post = new HttpPost(website);
		
		Time time = new Time();
		time.setToNow();
		String defaultLocation = "MyHouse";
		String defaultRadius = "10"; //10meters 
		
		try{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			//Add region takes in (time, radius, name) pairs
			nameValuePairs.add(new BasicNameValuePair("time", time.year+"-"+time.month+"-"+time.monthDay+" "+time.hour+":"+time.minute+":"+time.second));
			nameValuePairs.add(new BasicNameValuePair("radius", defaultRadius ));
			nameValuePairs.add(new BasicNameValuePair("name", defaultLocation ));
						
			client_post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			//POST THE LIST OF NAME VALUE PAIRS AND RETRIEVE RESPONSE
			HttpResponse response = client.execute(client_post);
			HttpEntity entity = response.getEntity();
			InputStream ins = entity.getContent();
			
			//RETRIEVE THE RESPONSE:
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
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}	

}
