package com.qlbs.Bridge.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class.getName());

	private static int defaultTimeout = 5000; // 默认超时时间5s

	private static RequestConfig config = RequestConfig.custom()//
			.setSocketTimeout(defaultTimeout)//
			.setConnectTimeout(defaultTimeout)//
			.setConnectionRequestTimeout(defaultTimeout)//
			.build();// Sets the connection timeout.

	/**
	 * Use httpGet to send request.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * @return String
	 * @date 2018年9月6日上午11:39:05
	 */
	public static String doGet(String url) throws Exception {
		return doGet(url, null, null);
	}

	/**
	 * Use httpGet to send request. If there are parameters, Advise to use
	 * doPost method.
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 * @return String
	 * @date 2018年9月6日上午11:39:12
	 */
	public static String doGet(String url, List<NameValuePair> params) throws Exception {
		return doGet(url, null, params);
	}

	/**
	 * Use httpGet to send request. Return null means the resultCode is not 200,
	 * maybe it happened something wrong during request the server. If the code
	 * is not 200, the response info is a html page.
	 * 
	 * @param url
	 * @param headers
	 *            allows to be null
	 * @param params
	 *            allows to be null
	 * @return
	 * @throws Exception
	 * @return String
	 * @date 2018年9月6日上午11:36:30
	 */
	public static String doGet(String url, List<NameValuePair> headers, List<NameValuePair> params) throws Exception {
		String result = null;

		// build url.
		URIBuilder builder = new URIBuilder(url);
		if (params != null) {
			for (NameValuePair param : params) {
				builder.addParameter(param.getName(), param.getValue());
			}
		}
		HttpGet httpGet = new HttpGet(builder.build());
		// set headers.
		if (params != null) {
			for (NameValuePair header : headers) {
				httpGet.addHeader(header.getName(), header.getValue());
			}
		}
		httpGet.setConfig(config);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int resultCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (resultCode == 200 && entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error("", e);
			return result;
		} catch (IOException e) {
			logger.error("", e);
			return result;
		} finally {
			if (response != null) {
				response.close();
			}
			httpClient.close();
		}
		return result;
	}

	public static String doPost(String url) throws Exception {
		return doPost(url, null, null);
	}

	public static String doPost(String url, List<BasicNameValuePair> params) throws Exception {
		return doPost(url, null, params);
	}

	/**
	 * In order to ensure correct deallocation of system resources, The user
	 * MUST call CloseableHttpResponse#close() from a finally clause.
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @throws Exception
	 * @return String
	 * @date 2018年9月6日下午12:00:30
	 */
	public static String doPost(String url, List<BasicNameValuePair> headers, List<BasicNameValuePair> params) throws Exception {
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(config);
		if (params != null)
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		if (headers != null) {
			for (BasicNameValuePair header : headers) {
				httpPost.addHeader(header.getName(), header.getValue());
			}
		}
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			response = httpClient.execute(httpPost);
			int resultCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (resultCode == 200 && entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error("", e);
			return result;
		} catch (IOException e) {
			logger.error("", e);
			return result;
		} finally {
			if (response != null) {
				response.close();
			}
			httpClient.close();
		}
		return result;
	}

	public static void main(String[] args) {
		String url = "https://sdkapiv2.youxifan.com/sdkapi/members/loginCheck";
		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("token", "1538986199"));
		// Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		map.put("token", "1545913072374902460");
		// String json = gson.toJson(map).toString();
		JSONObject json = new JSONObject(map);
		System.out.println(json.toJSONString());
		System.out.println(json.toString());
		String result = null;
		try {
			// result = doPost(url, json);
			result = doHttpPost(url, json.toJSONString(), ContentType.APPLICATION_JSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	/**
	 * 新增post方式发送请求, 可指定消息体类型
	 * 
	 * @param url
	 *            需要请求的url
	 * @param param
	 *            http请求的参数
	 * @param contentType
	 *            指定消息体类型
	 * @return
	 * @throws IOException
	 * @return String
	 * @date 2018年10月10日下午6:25:31
	 */
	public static String doHttpPost(String url, String param, ContentType contentType) {
		HttpPost httpPost = new HttpPost(url);
		StringEntity ectity = new StringEntity(param, contentType);
		// 不使用默认的编码格式, 全部转成utf8
		ectity.setContentEncoding("UTF-8");
		httpPost.setEntity(ectity);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String result = null;
		try {
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			result = EntityUtils.toString(entity);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return result;
	}

}
