package com.qlbs.Bridge.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpConnection {

	private static final int defaultTimeout = 10000;

	private String url;
	private String method;
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	private CallbackListener callbackListener;
	private CloseableHttpClient httpClient;
	private String result;
	private int responseCode;
	private RequestConfig config;
	private int timeout;

	public HttpConnection() {
		timeout = defaultTimeout;
		config = RequestConfig.custom()
				.setSocketTimeout(timeout)
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.build();// Sets the connection timeout.
		httpClient = HttpClients.createDefault();
	}
	
	public HttpConnection(int timeout) {
		config = RequestConfig.custom()
				.setSocketTimeout(timeout)
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.build();// Sets the connection timeout.
		httpClient = HttpClients.createDefault();
	}

	/**
	 * GET
	 * @param url
	 * @param callbackListener
	 * @return
	 */
	public static HttpConnection create(String url, CallbackListener callbackListener) {
		HttpConnection httpConnection = new HttpConnection();
		httpConnection.method = "GET";
		httpConnection.url = url;
		if (httpConnection.url.indexOf("?") > 0) {
			httpConnection.url += "&_t=" + System.currentTimeMillis();
		} else {
			httpConnection.url += "?_t=" + System.currentTimeMillis();
		}
		httpConnection.callbackListener = callbackListener;
		return httpConnection;
	}

	/**
	 * POST
	 * @param url
	 * @param callbackListener
	 * @return
	 */
	public static HttpConnection create(String url, List<NameValuePair> params, CallbackListener callbackListener) {
		HttpConnection httpConnection = new HttpConnection();
		httpConnection.url = url;
		httpConnection.method = "POST";
		if (params != null)
			httpConnection.params.addAll(params);
		httpConnection.callbackListener = callbackListener;
		return httpConnection;
	}

	public void exec(boolean async) {
		if (async) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					connect();
				}
			});
			t.start();
		} else {
			connect();
		}
	}

	private void connect() {
		HttpResponse httpResponse = null;
		try {
			if ("GET".equals(method)) {
				HttpGet httpGet = new HttpGet(url);
				httpGet.setConfig(config);
				httpResponse = httpClient.execute(new HttpGet(url));
			} else {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setConfig(config);
				httpPost.setEntity(new UrlEncodedFormEntity(this.params, "UTF-8"));
				httpResponse = httpClient.execute(httpPost);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			callbackListener.callBack(-1, "");
			return;
		}
		responseCode = httpResponse.getStatusLine().getStatusCode();
		result = "";
		try {
			result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			callbackListener.callBack(responseCode, "");
			return;
		}

		callbackListener.callBack(responseCode, result);
	}

	public String getResult() {
		return result;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public interface CallbackListener {
		public void callBack(final int responseCode, final String result);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String url = "http://47.92.122.53:8180/quick2_auth/quick2/auth";
		HttpConnection http = HttpConnection.create(url, params, new CallbackListener() {
			@Override
			public void callBack(int responseCode, String result) {
				System.out.println("result:" + result);
			}
		});
		http.exec(false);
	}

}
