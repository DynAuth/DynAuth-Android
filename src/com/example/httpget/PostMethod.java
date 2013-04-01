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

public class PostMethod {

	public String postInternetData() throws Exception{
		BufferedReader in = null;
		String data = null;
		URI website = new URI("http://cs5221.oko.io:8000/api/device/check-in");
		HttpClient client = new DefaultHttpClient();
		HttpPost client_post = new HttpPost(website);
		
		try{
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			//nameValuePairs.add(new BasicNameValuePair("POST", "http://cs5221.oko.io:8000/api/device/check-in HTTP/1.1"));
			//nameValuePairs.add(new BasicNameValuePair("ACCEPT", "*/*"));
			//nameValuePairs.add(new BasicNameValuePair("Content-Length", "102"));
			//nameValuePairs.add(new BasicNameValuePair("Accept-Encoding", "identity"));
			//nameValuePairs.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
			//nameValuePairs.add(new BasicNameValuePair("User-Agent", "NativeHost"));
			//nameValuePairs.add(new BasicNameValuePair("Host", "cs5221.oko.io:8000"));
			//nameValuePairs.add(new BasicNameValuePair("Connection", "Keep-Alive"));
			nameValuePairs.add(new BasicNameValuePair("device_id", "667252485e9c43fea5912ad1f291e787"));
			nameValuePairs.add(new BasicNameValuePair("latitude", "48.645"));
			nameValuePairs.add(new BasicNameValuePair("longitude", "-121.141"));
			nameValuePairs.add(new BasicNameValuePair("time", "2013-03-28+13:32:14"));
			client_post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			//POST THE LIST OF NAME VALUE PAIRS AND RETRIEVE RESPONSE
			HttpResponse response = client.execute(client_post);
			HttpEntity entity = response.getEntity();
			InputStream ins = entity.getContent();
			//NOW LETS RETRIEVE THE RESPONSE :D
			
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
