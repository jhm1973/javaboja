package com.javaboja.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.javaboja.dao.HistoryDao;
import com.javaboja.vo.History;
import com.javaboja.vo.Paging;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
		int listCnt = (int)historyDao.getHistoryCount(userId);
		Paging pagingVo = new Paging(listCnt, curPage);
		Pageable pageable = PageRequest.of(curPage-1, pagingVo.getPageSize());
		return historyDao.getHistorySelect(userId, pageable);
	}
	
	public List<History> getPopularList(){
		
		return historyDao.getPopularList();
	}
}
