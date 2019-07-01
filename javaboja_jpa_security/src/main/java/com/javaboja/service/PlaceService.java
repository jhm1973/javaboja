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
import com.javaboja.utils.HttpClientService;
import com.javaboja.utils.JsonConverter;
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
	public Page<Place> placeSearchService(String userId, String keyword, String url, String pageSize, String kakao_token, int curPage, boolean realSearch) {
		if(realSearch) {
			placeDao.placeDelete();
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
			placeDao.placeInsert(keyword, jsonArraylist);
			historyDao.historyInsert(keyword, userId);
		}
		return placeDao.placeSelect(keyword, curPage);
	}
}
