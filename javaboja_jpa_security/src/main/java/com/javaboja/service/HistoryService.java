package com.javaboja.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.javaboja.repository.HistoryRepository;
import com.javaboja.vo.History;
import com.javaboja.vo.Paging;

import lombok.extern.slf4j.Slf4j;

@Component
public class HistoryService {

	@Autowired
	HistoryDao historyDao;
	
	@Transactional
	public void historyInsert(String keyword, String userId) {
		historyDao.historyInsert(keyword, userId);
	}
	
	
	public long getHistoryCount(String userId) {
		return historyDao.getHistoryCount(userId);
	}
	
	public Page<History> getHistorySelect(String userId, int curPage){
		return historyDao.getHistorySelect(userId, curPage);
	}
	
	public List<History> getPopularList(){
		
		return historyDao.getPopularList();
	}
}
