package com.javaboja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaboja.vo.History;

public interface HistoryRepository extends JpaRepository<History, String>{
	public Page<History> findByUserIdOrderByCreateDateTimeDesc(String userId, Pageable pageable);
}
