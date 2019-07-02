package com.javaboja.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javaboja.vo.History;
import com.javaboja.vo.Place;

public interface PlaceRepository extends JpaRepository<Place, String>{

	public Place findByPlaceId(String placeId);
	//public Page<Place> findByKeywordAndUserId(String keyword, String userId, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("delete from Place p where p.userId = :userId")
	public void deleteAllByUserId(@Param("userId") String userId);
	
	public Place findByPlaceCodeAndUserId(String placeCode, String UserId);
	public int countByPlaceCodeAndUserId(String placeCode, String UserId);
}
