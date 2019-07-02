package com.javaboja.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.javaboja.dao.HistoryDao;
import com.javaboja.dao.PlaceDao;
import com.javaboja.repository.PlaceRepository;
import com.javaboja.utils.HttpClientUtil;
import com.javaboja.utils.JsonConverterUtil;
import com.javaboja.vo.Place;

import lombok.extern.slf4j.Slf4j;

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
	public String placeSearchService(String userId, String keyword, String url, String pageSize, String kakao_token, int curPage, boolean realSearch) {
		String response_data = hcs.httpClientGet(keyword, url, pageSize, curPage, kakao_token);
		//다른 키워드로 검색할 경우
		if(realSearch) {
			//pr.deleteAllByUserId(userId);
			historyDao.historyInsert(keyword, userId);
		}
		
		//placeDao.placeInsert(userId, jc.stringToJsonArray(response_data, "documents"));
		return response_data;
	}
	
	@Transactional
	public String placeDetail(String id, String keyword, String url, String pageSize, int curPage, String kakao_token) {
		String response_data = hcs.httpClientGet(keyword, url, pageSize, curPage, kakao_token);
		log.info("carrey : "+response_data);
		JSONArray jsonArray = jc.stringToJsonArray(response_data, "documents");
		JSONObject detailObject = new JSONObject();
		for(int i=0; i<jsonArray.size(); i++) {
			JSONObject tmpObj = (JSONObject) jsonArray.get(i);
			if(id.equals(tmpObj.get("id").toString())) {
				detailObject= tmpObj;
			}
		}
		return detailObject.toJSONString();
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
