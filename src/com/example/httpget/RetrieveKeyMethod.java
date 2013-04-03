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
import android.widget.Toast;

public class RetrieveKeyMethod extends Activity {
		
public StringBuilder keyData() throws Exception{
		
		String data = null;
		URI website = new URI("http://cs5221.oko.io:8000/api/service/request-device-key");
		HttpClient client = new DefaultHttpClient();
		HttpPost client_post = new HttpPost(website);
				
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		
		//Retrieve registration key by sending (api_key) pair
		nameValuePairs.add(new BasicNameValuePair("api_key", "570915b1b5cb4db6981f463b48d09ad8"));
		
		client_post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		//POST THE LIST OF NAME VALUE PAIRS AND RETRIEVE RESPONSE
		HttpResponse response = client.execute(client_post);
		HttpEntity entity = response.getEntity();
		InputStream ins = entity.getContent();

		BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		try{
			while ((line = reader.readLine()) != null) {
				sb.append((line));
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
		
		return sb;
	}
}
		
	
	
	
	
	
		

