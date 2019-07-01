package com.javaboja.execution;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Component
@Slf4j
public class HttpClientService{

	public String httpClientGet(String keyword, String url, String pageSize, int page, String kakaoToken) {
		log.info("url : "+url+"query="+keyword);
		JsonConverter jsonConverter = new JsonConverter();
		String responseBody = "";
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(60, TimeUnit.MINUTES)
				.followRedirects(true)
				.readTimeout(20, TimeUnit.MINUTES)
				.retryOnConnectionFailure(false)
				.writeTimeout(20, TimeUnit.MINUTES)
				.connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
				.build();
		Request request = null;
		try {
			request = new Request.Builder()
	        		.addHeader("Authorization", "KakaoAK " + kakaoToken)
	        		.url(url+"query="+keyword+
	        				 "&size="+pageSize+
	        				 "&page="+page
	        				)
	        		.build();
			responseBody = client.newCall(request).execute().body().string();
			
			jsonConverter.StringToJsonObject(responseBody,"meta");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
}
