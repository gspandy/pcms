package com.pujjr.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil
{
	/**
	 * ʹ��apache httpclient ����post����
	 * @param url �����ַ
	 * @param params ����
	 * @return ���󷵻�ֵ
	 * @throws Exception �׳��쳣
	 */
	public static String request(String url, List<NameValuePair> params)
			throws Exception {

		String result = null;

		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params,
				"UTF-8");
		httppost.setEntity(uefEntity);

		// �������������ݴ���ĳ�ʱʱ��
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
				60000).setConnectTimeout(20000).build();
		httppost.setConfig(requestConfig);

		// �ύ����
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity, "UTF-8");
		} else {
			httppost.releaseConnection();
			throw new Exception();
		}
		httppost.releaseConnection();
		return result;
	}
}
