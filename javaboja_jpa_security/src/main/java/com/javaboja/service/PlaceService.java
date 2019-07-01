package com.javaboja.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.javaboja.execution.HttpClientService;
import com.javaboja.execution.JsonConverter;
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
	HttpClientService hcs;
	@Autowired
	JsonConverter jc;
	
	@Transactional
	public Page<Place> placeSearchServce(String userId, String keyword, String url, String pageSize, String kakao_token, int curPage) {
		 
		int keywordSearchCount = (int)historyDao.getHistoryKeywordCount(userId, keyword);
		if(keywordSearchCount==0) {
			String httpResponse = ""; 
			String is_end = "";
			List<JSONArray> jsonArraylist = new ArrayList<>();
			int page=1;
			while(true) {
				httpResponse = hcs.httpClientGet(keyword, url, pageSize, page, kakao_token);
			 	is_end=jc.StringToJsonObject(httpResponse, "meta").get("is_end").toString();
				jsonArraylist.add(jc.stringToJsonArray(httpResponse,"documents"));
				if(is_end.equals("true")) break;
				page++;
			}
			log.info("carrey : "+jsonArraylist);
			placeDao.placeInsert(keyword, jsonArraylist);
		}
		placeDao.getPlaceCount();
		historyDao.historyInsert(keyword, userId);
		return placeDao.placeSelect(keyword, curPage);
	}
}
