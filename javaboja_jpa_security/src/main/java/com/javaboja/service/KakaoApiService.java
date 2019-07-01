/*
 * package com.javaboja.service;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component;
 * 
 * import com.javaboja.execution.HttpClientService; import
 * com.javaboja.execution.JsonConverter;
 * 
 * @Component public class KakaoApiService {
 * 
 * @Autowired HttpClientService hcs;
 * 
 * @Autowired JsonConverter jc;
 * 
 * @Autowired PlaceDaoService pds;
 * 
 * public String PlaceInsert(String kewyword, String url, String pageSize,
 * String curPage,String kakaoToken) {
 * 
 * 
 * String httpResponse = hcs.httpClientGet(kewyword, url, pageSize, curPage,
 * kakaoToken); String is_end=jc.StringToJsonObject(httpResponse,
 * "meta").get("is_end").toString();
 * 
 * 
 * 
 * return null; } }
 */