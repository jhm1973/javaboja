package com.javaboja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaboja.vo.History;
import com.javaboja.vo.Place;

public interface PlaceRepository extends JpaRepository<Place, String>{

	public Place findByPlaceId(String placeId);
	//public Page<Place> findAll(Pageable pageable);
	public Page<Place> findByKeyword(String keyword, Pageable pageable);
}
