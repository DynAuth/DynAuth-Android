package com.example.datacollector;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button startBtn;  
    private Button stopBtn;  
    private Button sendBtn;
    public Http_post hp = new Http_post();
    List<String> name = new ArrayList<String>();
	List<String> value = new ArrayList<String>();
    @Override 
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        startBtn = (Button) findViewById(R.id.startBtn);  
        stopBtn = (Button) findViewById(R.id.stopBtn); 
        //sendBtn = (Button) findViewById(R.id.sendBtn);
        //添加监听器  
        startBtn.setOnClickListener(listener);  
        stopBtn.setOnClickListener(listener);
        //sendBtn.setOnClickListener(listener); 
    }  
      
    //启动监听器  
    private OnClickListener listener=new OnClickListener()  
    {         
        @Override 
        public void onClick(View v)  
        {  
            Intent intent=new Intent(MainActivity.this, Upload_info.class);  
            switch (v.getId())  
            {  
            case R.id.startBtn:  
                startService(intent);  
                break;  
            case R.id.stopBtn:  
                stopService(intent);  
                break;
            /*case R.id.sendBtn:
            	String IMEI="123456";
            	String cpu_info="%56";
            	String mem_info = "%56";
            	String ip_address = "223.101.11.11";
            	String process = "QQ\nMSN\n";
            	name.add("IMEI");
				value.add(IMEI);
				name.add("cpu_info");
				value.add(cpu_info);
				name.add("mem_info");
				value.add(mem_info);
				name.add("ip_address");
				value.add(ip_address);
				name.add("process");
				value.add(process);
            	hp.post(name, value);
            	break;*/
            default:  
                break;  
            }             
        }  
    };  

}
