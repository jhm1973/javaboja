package com.javaboja.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
@Slf4j
public class HttpClientUtil{

	public Response httpClientGet(String keyword, String url, String pageSize, int page, String kakaoToken) throws Exception{
		Response response = null;
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(60, TimeUnit.MINUTES)
				.followRedirects(true)
				.readTimeout(20, TimeUnit.MINUTES)
				.retryOnConnectionFailure(false)
				.writeTimeout(20, TimeUnit.MINUTES)
				.connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
				.build();
		Request request = null;
			request = new Request.Builder()
	        		.addHeader("Authorization", "KakaoAK " + kakaoToken)
	        		.url(url+"query="+keyword+
	        				 "&size="+pageSize+
	        				 "&page="+page
	        				)
	        		.build();
			response = client.newCall(request).execute();
		return response;
	}
}
