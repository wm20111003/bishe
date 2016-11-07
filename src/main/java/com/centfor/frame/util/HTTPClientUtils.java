package com.centfor.frame.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HTTPClientUtils {
	//private static Logger logger = LoggerFactory.getLogger(HTTPUtils.class);

	/**
	 * 发送Get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String get(String url)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		// ==============httppGet开始=================
		HttpGet httpget = new HttpGet(url);
		// httpget.addHeader(new BasicHeader("", ""));
		// httpget.addHeader("", "");
		CloseableHttpResponse httpReponse = httpclient.execute(httpget);
		try {
			// 获取状态行
			HttpEntity entity = httpReponse.getEntity();
			// 返回内容
			result = EntityUtils.toString(entity);
		} finally {
			httpReponse.close();
			httpclient.close();
		}
		return result;
	}

	/**
	 * 发送 Post请求
	 * 
	 * @param url
	 * @param reqXml
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String post(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		if(params!=null){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
		}
		

		CloseableHttpResponse httppHttpResponse = httpclient.execute(httpPost);

		try {
			result = EntityUtils.toString(httppHttpResponse.getEntity());
		} finally {
			httppHttpResponse.close();
			httpclient.close();
		}

		return result;
	}



}
