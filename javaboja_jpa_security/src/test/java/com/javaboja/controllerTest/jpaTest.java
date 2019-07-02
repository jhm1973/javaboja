package com.javaboja.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaboja.repository.PlaceRepository;
import com.javaboja.vo.History;
import com.javaboja.vo.Place;

@RunWith(SpringRunner.class)
@DataJpaTest
public class jpaTest {

//	@Autowired
//	private TestEntityManager em;
	@Autowired
	private EntityManager em;
	@Autowired
	private PlaceRepository pr;
	
	//데이터 추가 삭제
//	@Test
//	public void deletePlace() {
//		for(int i=1;i<=10;i++) {
//			Place place = new Place();
//			place.setAddressName("주소");
//			place.setKeyword("키워드");
//			place.setLatitude("위도");
//			place.setLongitude("경도");
//			place.setPhone("전화");
//			place.setUserId("javaboja1");
//			em.persist(place);
//		}
//		
//		for(int i=1;i<=10;i++) {
//			Place place2 = em.find(Place.class, i);
//			assertThat(place2.getUserId()).isEqualTo("javaboja1");
//		}
//		
//		assertThat(pr.count()).isEqualTo(10);
//		
//		pr.deleteAllByUserId("javaboja1");
//		
//		assertThat(pr.count()).isEqualTo(0);
//		
//	}
	
	//bulk insert test
	@Test
	public void bulkInsert() {
//		List<Place> list = new ArrayList<>();
//		for(int i=0; i<10000; i++) {
//			Place place = new Place();
//			place.setAddressName("주소");
//			place.setKeyword("키워드");
//			place.setLatitude("위도");
//			place.setLongitude("경도");
//			place.setPhone("번호");
//			place.setPlaceName("장소명");
//			place.setPlaceUrl("URL");
//			place.setRoadAddressName("도로명주소");
//			place.setUserId("javaboja");
//			list.add(place);
//		}
//		pr.saveAll(list);
		
		//밑에가 약간 더 빠름
		for(int i=0; i<10000; i++) {
			Place place = new Place();
			place.setAddressName("주소");
			place.setKeyword("키워드");
			place.setLatitude("위도");
			place.setLongitude("경도");
			place.setPhone("번호");
			place.setPlaceName("장소명");
			place.setPlaceUrl("URL");
			place.setRoadAddressName("도로명주소");
			place.setUserId("javaboja");

			em.persist(place);
		}
	}
//	@Test
//	public void getHistoryKeywordCount() {
//		History history = new History();
//		
//		String jpql = "select count(h.historyId) "+ 
//				  "from History h "+
//				  "where user_id = :user_id "+ 
//				  "and keyword = :keyword";
//		Query query =em.createQuery(jpql);
//		query.setParameter("user_id", "javaboja1");
//		query.setParameter("keyword", "테스트");
//	}
}
