package com.javaboja.service;

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
	
	public Page<History> getHistorySelect(String userId, int curPage){
		int listCnt = (int)getHistoryCount(userId);
		Paging pagingVo = new Paging(listCnt, curPage);
		Pageable pageable = PageRequest.of(curPage-1, pagingVo.getPageSize());
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
//		String jpql = "select h.keyword, "
//							+ "count(h.keyword) h.views "
//							+ "from History h "
//							+ "group by h.keyword "
//							+ "order by h.views desc ";
		String sql = "select keyword, "
					+ "count(keyword) views "
					+ "from History "
					+ "group by keyword "
					+ "order by views desc ";
		Query query = em.createNativeQuery(sql);
		List list = query.setMaxResults(10).getResultList();
		for(int i=0;i<list.size();i++) {
			log.info("carrey : "+list.get(i));
		}
		return null;
	}
}
