package com.example.datacollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class Basic_info extends Activity{
	public Resources res=null;
	//public static Http_post hp = new Http_post();
	public static String IMEI = null;
	
	
	public static void initialize()
	{
		
	}
	public static String fetch_version_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/cat", "/proc/version" };
			result = cmdexe.run(args, "system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public static String fetch_IMEI(Context cx)
	{
		TelephonyManager tm = (TelephonyManager) cx
				.getSystemService(Context.TELEPHONY_SERVICE);
		//String str = "";
		/**
		 * httppost
		 */
		IMEI = tm.getDeviceId();
		return IMEI;
	}

	public static String fetch_tel_status(Context cx) {
		List<String> name = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		String result = null;
		TelephonyManager tm = (TelephonyManager) cx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String str = "";
		/**
		 * httppost
		 */
		IMEI = tm.getDeviceId();
		name.add("IMEI");
		value.add(IMEI);

		// hp.post(name, value);

		// str+="Success added into webserver!\n";
		str += "DeviceId(IMEI) = " + IMEI + "\n";
		str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion()
				+ "\n";
		// TODO: Do something ...
		int mcc = cx.getResources().getConfiguration().mcc;
		int mnc = cx.getResources().getConfiguration().mnc;
		str += "IMSI MCC (Mobile Country Code): " + String.valueOf(mcc) + "\n";
		str += "IMSI MNC (Mobile Network Code): " + String.valueOf(mnc) + "\n";
		result = str;
		return result;
	}

	public static String fetch_cpu_info() {
		String result = null;
		List<String> name = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
			name.add("cpu_info");
			name.add("IMEI");
			result = cmdexe.run(args, "/system/bin/");
			value.add(result);
			value.add(IMEI);
			// hp.post(name, value);
			//Log.i("result-fuck", "result=" + result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 *系统内存情况查看
	 */
	public static String getMemoryInfo(Context context) {
		StringBuffer memoryInfo = new StringBuffer();

		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(outInfo);

		//memoryInfo.append("\nTotal Available Memory :").append(
			//	outInfo.availMem >> 10).append("K");
		memoryInfo.append("\nTotal Available Memory :").append(
				outInfo.availMem >> 20).append("M");
		memoryInfo.append("\nIn low memory situation:").append(
				outInfo.lowMemory);

		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/cat", "/proc/meminfo" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info", "ex=" + ex.toString());
		}
		return (memoryInfo.toString() + "\n\n" + result);
	}

	// 磁盘信息
	public static String fetch_disk_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/df" };
			result = cmdexe.run(args, "/system/bin/");
			//Log.i("result", "result=" + result);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
	//网络信息
	/*public static String fetch_netcfg_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		Log.e("Yang", "String fetch_netcfg_info()");
		try {
			String[] args = { "/system/bin/netcfg" };
			result = cmdexe.run(args, "/system/bin/");
			String regEx="(?<=eth0[\\s]{5}UP[\\s]{4})[\\S]+(?=\\s)";
			Log.i("Basic_info", regEx);
			Pattern p=Pattern.compile(regEx);
			Matcher m=p.matcher(result);
			boolean br=m.find();
			Ip_addr.getLocalIPAddress()=m.group();
			Log.e("Yang", Ip_addr.getLocalIPAddress());
			//int i;
			//for(i=0;i<ipaddress.length();i++)
				//Log.i("Basic info ipaddr", ""+ipaddress.charAt(i));
			Log.i("Basic_info", m.group());
		} catch (Exception ex) {
			Log.i("fetch_netcfg_info", "ex=" + ex.toString());
		}
		return result;
	}*/

	public static String getDisplayMetrics(Context cx) {
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		str += "The absolute width: " + String.valueOf(screenWidth)
				+ "pixels\n";
		str += "The absolute heightin: " + String.valueOf(screenHeight)
				+ "pixels\n";
		str += "The logical density of the display. : "
				+ String.valueOf(density) + "\n";
		str += "X dimension : " + String.valueOf(xdpi) + "pixels per inch\n";
		str += "Y dimension : " + String.valueOf(ydpi) + "pixels per inch\n";
		return str;
	}
}
