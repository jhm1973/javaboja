package com.javaboja.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.javaboja.dao.HistoryDao;
import com.javaboja.dao.PlaceDao;
import com.javaboja.repository.PlaceRepository;
import com.javaboja.utils.HttpClientUtil;
import com.javaboja.utils.JsonConverterUtil;
import com.javaboja.vo.Place;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Component
@Slf4j
public class PlaceService {

	@Autowired
	PlaceDao placeDao;
	@Autowired
	HistoryDao historyDao;
	@Autowired
	HttpClientUtil hcs;
	@Autowired
	PlaceRepository pr;
	@Autowired
	JsonConverterUtil jc;
	
	@Transactional
	public ResponseEntity<String> placeSearchService(String userId, String keyword, String url, String pageSize, String kakao_token, int curPage, boolean realSearch) {
		Response response = null;
		String responseBody = "";
		ResponseEntity<String> responseEntity = null;
		try {
			response = hcs.httpClientGet(keyword, url, pageSize, curPage, kakao_token);
			responseBody = response.body().string();
			if(response.isSuccessful()) {
				//다른 키워드로 검색할 경우
				if(realSearch) {
					historyDao.historyInsert(keyword, userId);
				}
				responseEntity = new ResponseEntity<String>(responseBody, HttpStatus.OK);
			}else {
				responseEntity = new ResponseEntity<String>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}catch(Exception e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
	@Transactional
	public ResponseEntity<String> placeDetail(String id, String keyword, String url, String pageSize, int curPage, String kakao_token) {
		Response response = null;
		String responseBody = "";
		ResponseEntity<String> responseEntity = null;
		try {
			response = hcs.httpClientGet(keyword, url, pageSize, curPage, kakao_token);
			if(response.isSuccessful()) {
				responseBody = response.body().string();
				JSONArray jsonArray = jc.stringToJsonArray(responseBody, "documents");
				JSONObject detailObject = new JSONObject();
				for(int i=0; i<jsonArray.size(); i++) {
					JSONObject tmpObj = (JSONObject) jsonArray.get(i);
					if(id.equals(tmpObj.get("id").toString())) {
						detailObject= tmpObj;
					}
				}
				responseEntity = new ResponseEntity<String>(detailObject.toJSONString(), HttpStatus.OK);
			}else {
				responseEntity = new ResponseEntity<String>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
		return responseEntity;
	}
//	@Transactional
//	public Page<Place> placeSearchService(String userId, String keyword, String url, String pageSize, String kakao_token, int curPage, boolean realSearch) {
//		if(realSearch) {
//			pr.deleteAllByUserId(userId);
//			String httpResponse = ""; 
//			String is_end = "";
//			List<JSONArray> jsonArraylist = new ArrayList<>();
//			int page=1;
//			while(true) {
//				httpResponse = hcs.httpClientGet(keyword, url, pageSize, page, kakao_token);
//			 	is_end=jc.StringToJsonObject(httpResponse, "meta").get("is_end").toString();
//				jsonArraylist.add(jc.stringToJsonArray(httpResponse,"documents"));
//				if(is_end.equals("true")) break;
//				page++;
//			}
//			placeDao.placeInsert(userId, keyword, jsonArraylist);
//			historyDao.historyInsert(keyword, userId);
//		}
//		return placeDao.placeSelect(userId, keyword, curPage);
//	}
}
