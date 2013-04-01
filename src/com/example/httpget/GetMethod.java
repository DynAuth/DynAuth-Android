package com.example.httpget;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetMethod {

	public String getInternetData() throws Exception{
		BufferedReader in = null;
		String data = null;
		try{
			//Define HTTP CLIENT
			HttpClient client = new DefaultHttpClient();
			
			//Define Web address
			URI website = new URI("http://cs5221.oko.io:8000/api/device/check-in");
			
			//Define HTTPGET request
			HttpGet request = new HttpGet();
			
			//Cast URI to HTTPGET request
			request.setURI(website);
			
			//Define HTTPRESPONSE and set it equal to the execution of HTTP CLIENT's REQUEST
			HttpResponse response = client.execute(request);
			
			//Define INPUT STREAM
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			//Define STRING BUFFER, LINE (l),and NEW LINE (nl)
			StringBuffer sb = new StringBuffer("");
			String l = "";
			String nl = System.getProperty("line.separator"); //Line separator looks for the end of the lines
			
			//Fill up the STRING BUFFER 
			while((l = in.readLine()) !=null){
				sb.append(l + nl);
				
			}
			
			//Close the INPUT STREAM
			in.close();
			
			//Set your DATA value to youe STRING BUFFER (which is converted toString)
			data = sb.toString();
			
			//Return the data
			return data;
			
		}finally{
			if(in != null){
				try{
					in.close();
					return data;
				}catch (Exception e){
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
}
