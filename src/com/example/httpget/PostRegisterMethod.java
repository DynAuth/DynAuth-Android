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
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class PostRegisterMethod extends Activity {
	
	RetrieveKeyMethod getKey = new RetrieveKeyMethod();
	
	
	public String postInternetData() throws Exception{
		
		String data = null;
		URI website = new URI("http://cs5221.oko.io:8000/api/device/register");
		HttpClient client = new DefaultHttpClient();
		HttpPost client_post = new HttpPost(website);
		
		String regKey = getKey.keyData().toString();
		String defaultUsername = "gerr0041";
		String defaultPassword = "porkchop";
		
		//EditText ueditText = (EditText)findViewById(R.id.usernameText);
		//ueditText.setText("Username", TextView.BufferType.EDITABLE);
		//EditText peditText = (EditText)findViewById(R.id.passwordText);
		//peditText.setText("Password", TextView.BufferType.EDITABLE);

		//String user = ueditText.getText().toString();
		//String pass = peditText.getText().toString();
		
		try{
						
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			//REGISTER = device_key, device_name, username, password
			
			nameValuePairs.add(new BasicNameValuePair("username", defaultUsername));
			nameValuePairs.add(new BasicNameValuePair("password", defaultPassword));
			nameValuePairs.add(new BasicNameValuePair("device_key", regKey));
			nameValuePairs.add(new BasicNameValuePair("device_name", "gerr0041"));
			
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
