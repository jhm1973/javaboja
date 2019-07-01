package com.javaboja.service;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.javaboja.execution.HttpClientService;
import com.javaboja.execution.JsonConverter;
import com.javaboja.repository.PlaceRepository;
import com.javaboja.vo.Paging;
import com.javaboja.vo.Place;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PlaceDao {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	HttpClientService hcs;
	@Autowired
	JsonConverter jc;
	@Autowired
	PlaceRepository placeRepository;
	
//	@Transactional 
//	 public void placeInsert(String kewyword, String url, String pageSize, String kakaoToken) { 
//		 String httpResponse = ""; 
//		 String is_end = "";
//		 JSONArray placeArray = null;
//		 int page=1; 
//		 while(true) { 
//			 
//			 httpResponse = hcs.httpClientGet(kewyword, url, pageSize, page, kakaoToken);
//			 is_end=jc.StringToJsonObject(httpResponse, "meta").get("is_end").toString();
//			 placeArray=jc.stringToJsonArray(httpResponse,"documents");
//				  for(int i=0; i<placeArray.size(); i++) {
//							JSONObject tmpObj = (JSONObject) placeArray.get(i);
//							Place place = new Place();
//							place.setAddressName(tmpObj.get("address_name").toString());
//							place.setPhone(tmpObj.get("phone").toString());
//							place.setPlaceId(tmpObj.get("id").toString());
//							place.setPlaceName(tmpObj.get("place_name").toString());
//							place.setPlaceUrl(tmpObj.get("place_url").toString());
//							place.setRoadAddressName(tmpObj.get("road_address_name").toString());
//							place.setLongitude(tmpObj.get("x").toString());
//							place.setLatitude(tmpObj.get("y").toString());
//							em.persist(place);
//				}
//			 if(is_end.equals("true")) break;
//			 page++;
//		 }
//	 }
	@Transactional 
	public void placeInsert(String keyword, List<JSONArray> list) { 
		JSONArray placeArray = null;
		JSONObject tmpObj = null;
		Place place = null;
		for(int i=0; i<list.size(); i++) {
			  placeArray = list.get(i);
			  
			  for(int j=0; j<placeArray.size();j++) {
			  	 tmpObj = (JSONObject) placeArray.get(j);
				 place = new Place();
				 place.setAddressName(tmpObj.get("address_name").toString());
				 place.setPhone(tmpObj.get("phone").toString());
				 place.setPlaceId(tmpObj.get("id").toString());
				 place.setPlaceName(tmpObj.get("place_name").toString());
				 place.setPlaceUrl(tmpObj.get("place_url").toString());
				 place.setRoadAddressName(tmpObj.get("road_address_name").toString());
				 place.setLongitude(tmpObj.get("x").toString());
				 place.setLatitude(tmpObj.get("y").toString());
				 place.setKeyword(keyword);
				 em.persist(place);
				 tmpObj = null;
				 place = null;
			  }
			  placeArray=null;
		}
			
	}
	 
	public long getPlaceCount() {
		String jpql = "select count(p.placeId) from Place p";
		Query query =em.createQuery(jpql);
		return (long)query.getSingleResult();
	} 
	 
	public Page<Place> placeSelect(String keyword, int curPage){
		 int listCnt = (int)getPlaceCount();
		 Paging pagingVo = new Paging(listCnt, curPage);
		 Pageable pageable = PageRequest.of(curPage-1, pagingVo.getPageSize());
		 return placeRepository.findByKeyword(keyword, pageable);
	}
	 
//	 @Transactional
//		public void placeInsert(JSONArray placeArray) {
//			PlaceVo placeVo = null;
//			for(int i=0; i<placeArray.size(); i++) {
//				JSONObject tmpObj = (JSONObject) placeArray.get(i);
//				if(em.find(PlaceVo.class, tmpObj.get("id").toString())==null) {
//					placeVo = new PlaceVo();
//					placeVo.setAddressName(tmpObj.get("address_name").toString());
//					placeVo.setPhone(tmpObj.get("phone").toString());
//					placeVo.setPlaceId(tmpObj.get("id").toString());
//					placeVo.setPlaceName(tmpObj.get("place_name").toString());
//					placeVo.setPlaceUrl(tmpObj.get("place_url").toString());
//					placeVo.setRoadAddressName(tmpObj.get("road_address_name").toString());
//					placeVo.setLongitude(tmpObj.get("x").toString());
//					placeVo.setLatitude(tmpObj.get("y").toString());
//					em.persist(placeVo);
//				}		
//			}
//		}
	
}
