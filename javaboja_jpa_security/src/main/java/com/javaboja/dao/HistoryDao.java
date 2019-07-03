package com.javaboja.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityResult;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
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

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HistoryDao {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	HistoryRepository historyRepository;

	public void historyInsert(String keyword, String userId) {
		History historyVo = new History();
		historyVo.setKeyword(keyword);
		historyVo.setUserId(userId);
		em.persist(historyVo);
	}
	
	public long getHistoryCount(String userId) {
		String jpql = "select count(h.historyId) from History h where user_id = :user_id";
		Query query =em.createQuery(jpql);
		query.setParameter("user_id", userId);
		return (long)query.getSingleResult();
	}
	
	public Page<History> getHistorySelect(String userId, Pageable pageable){
		return historyRepository.findByUserIdOrderByCreateDateTimeDesc(userId, pageable);
	}
	
	public long getHistoryKeywordCount(String userId, String keyword){
		String jpql = "select count(h.historyId) "+ 
					  "from History h "+
					  "where user_id = :user_id "+ 
					  "and keyword = :keyword";
		
		Query query =em.createQuery(jpql);
		query.setParameter("user_id", userId);
		query.setParameter("keyword", keyword);
		return (long)query.getSingleResult();
	}
	
	public List<History> getPopularList(){
		String sql = "select keyword, "
					+ "count(keyword) views "
					+ "from History "
					+ "group by keyword "
					+ "order by views desc ";
		Query query = em.createNativeQuery(sql);
		List<Object[]> queryResult = query.setMaxResults(10).getResultList();
		List<History> popularList = new ArrayList<History>();

		for(int i=0; i<queryResult.size();i++) {
				Object object[] = queryResult.get(i);
				History history = new History();
				history.setKeyword(object[0].toString());
				history.setViews(Integer.parseInt(object[1].toString()));
				popularList.add(history);
		}
		return popularList;
	}
}
