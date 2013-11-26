package com.example.datacollector;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class OtherActivity extends Activity {
	private EditText editText1=null; 
	private String result;
	private String activity_id;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.asdfmain);
		Intent intent=getIntent();
		String type=intent.getStringExtra("type");
		activity_id=intent.getStringExtra("activity_id");
		String question=intent.getStringExtra("question");
		//int number=intent.getIntExtra("number", 0);
		//int number2=intent.getIntExtra("number2", 0);
		TextView textView=(TextView)this.findViewById(R.id.textView1);
		textView.setText("question: "+question);
		//textView.setText("gongsiming: "+company+"; number: "+number+"; number2: "+number2);
		editText1 = (EditText) findViewById(R.id.editText1); 
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	public void closeActivity(View v)
	{
		Intent data=new Intent();
		data.putExtra("result", "kkkkkk");
		setResult(30,data);
		this.finish();
	}
	public void submitActivity(View v)
	{
		HttpPost request = new HttpPost("http://"
				+ Ip_addr.server_ip_address
				//+ "/bbb/storeMobileInfomation.php");
				+ "/DynAuth/storeMobileInfomation.php");
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// data constructor
		// IMEI OK
		String answer = editText1.getText().toString();
		Log.i("Other", answer);
		params.add(new BasicNameValuePair("detailedActivity", answer));
		params.add(new BasicNameValuePair("activity_id", activity_id));
		//Log.i("Yang", answer);
		try {
			//Log.i("echo", "here we go");
			request.setEntity(new UrlEncodedFormEntity(params,
					HTTP.UTF_8));
			HttpResponse response = new DefaultHttpClient()
					.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response
						.getEntity());
				// Toast.makeText(this, result,
				// Toast.LENGTH_LONG).show();
				//writeNewFileData("sql.txt", "");
				//writeNewFileData("sql.txt", result);
				Log.i("echo", result);
			}
			else
			{
				Log.i("echo", "something wrong");
			}
		} catch (Exception e) {

			// Toast.makeText(this, e.getMessage().toString(),
			// Toast.LENGTH_LONG).show();
			Log.i("ghostli123456", e.getMessage().toString());
			e.printStackTrace();
		}
		this.finish();

	}
}
