package com.javaboja.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonConverter {

	public JSONArray stringToJsonArray(String data, String searchType) {
		JSONParser jp = null;
		JSONObject jo = null;
		JSONArray placeArray = null;
		
		try {
			jp = new JSONParser();
			jo = (JSONObject)jp.parse(data);
			placeArray = (JSONArray)jo.get(searchType);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return placeArray;
	}
	
	public JSONObject StringToJsonObject(String data, String searchType) {
		JSONParser jp = null;
		JSONObject jo = null;
		try {
			jp = new JSONParser();
			jo = (JSONObject)jp.parse(data);
			jo = (JSONObject)jo.get(searchType);
			log.info(jo.get("is_end").toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
}
