package com.javaboja.httpClientTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaboja.utils.HttpClientUtil;

import okhttp3.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientTest {
	
	@Test
	public void HttpClientTest() throws Exception{
		HttpClientUtil hc = new HttpClientUtil();
		String keyword="카카오";
		String url="https://dapi.kakao.com/v2/local/search/keyword.json?";
		String pageSize="10";
		int page=1;
		String kakaoToken="abeb874a895f679ebd682dcb8b69b51e";
		Response response = hc.httpClientGet(keyword, url, pageSize, page, kakaoToken);
		System.out.println(response.body().string());
	}
}
