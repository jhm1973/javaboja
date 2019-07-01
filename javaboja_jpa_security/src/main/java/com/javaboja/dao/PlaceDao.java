package com.javaboja.dao;



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

import com.javaboja.repository.PlaceRepository;
import com.javaboja.utils.HttpClientService;
import com.javaboja.utils.JsonConverter;
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
	
	public void placeDelete() { 
		String jpql = "delete from Place";
		em.createQuery(jpql).executeUpdate();
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
}
