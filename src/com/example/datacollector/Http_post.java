package com.example.datacollector;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;


public class Http_post {

	// String urlAPI="http://"+Ip_addr.ip_address+"/mobile_info/getval.php";
	// HttpClient myclient = new DefaultHttpClient();
	public String post(List<String> name, List<String> value) {
		if (Ip_addr.server_ip_address == null) {
			return "failed!";
		}
		//String urlAPI = "http://" + Ip_addr.ip_address
		//		+ "/mobile_info/getval.php";
		//String urlAPI = "http://" + Ip_addr.ip_address + "/mobile_info/getval.php";
		String urlAPI = "http://" + Ip_addr.server_ip_address + "/DyAuthen/storeMobileInfomation.php";
		// post参数
		List<NameValuePair> post_params = new ArrayList<NameValuePair>();

		for (int i = 0; i < name.size(); i++) {
			post_params.add(new BasicNameValuePair(name.get(i), value.get(i)));
		}

		// http 参数
		HttpParams http_params = new BasicHttpParams();

		try {

			HttpConnectionParams.setConnectionTimeout(http_params, 2 * 1000);
			HttpConnectionParams.setSoTimeout(http_params, 2 * 1000);
			HttpConnectionParams.setSocketBufferSize(http_params, 8192);
			// 创建一个client
			HttpClient myClient = new DefaultHttpClient(http_params);
			// 创建一个post
			HttpPost mypost = new HttpPost(urlAPI);
			// 发出HTTP request
			mypost.setEntity(new UrlEncodedFormEntity(post_params, HTTP.UTF_8));
			// 取得HTTP response
			HttpResponse httpResponse = myClient.execute(mypost);

			// 若状态码为200 ok
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 取出回应字串
				return httpResponse.getParams().toString();

			} else {
				return Integer.toString((httpResponse.getStatusLine()
						.getStatusCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed!";
	}
}
