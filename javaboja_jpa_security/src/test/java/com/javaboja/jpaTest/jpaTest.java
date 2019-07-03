package com.javaboja.jpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import com.javaboja.service.PlaceService;
import com.javaboja.vo.History;
import com.javaboja.vo.Paging;
import com.javaboja.vo.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class jpaTest {
	
	@Test
	public void contextLoads() {
		
	}
//	@Autowired
//	private EntityManager em;
//	//데이터 추가 삭제
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
//	@Test
//	public void bulkInsert() {
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
		
		//managerEntity 사용 시 더 빠름
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
//
//			em.persist(place);
//		}
//	}
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
//	@Test
//	public void placeSelect() {
//		
//		for(int i=0; i<15; i++) {
//			Place place = new Place();
//			place.setAddressName("주소");
//			place.setKeyword("키워드");
//			place.setLatitude("위도");
//			place.setLongitude("경도");
//			place.setPhone("번호");
//			place.setPlaceName("장소명");
//			place.setPlaceUrl("URL");
//			place.setRoadAddressName("도로명주소");
//			place.setUserId("javaboja1");
//
//			em.persist(place);
//		}
//		assertEquals(15, pr.count());
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Place> list = pr.findByKeywordAndUserId("키워드", "javaboja1", pageable);
//		for(int i=0;i<list.getSize();i++) {
//			System.out.println("number : "+list.getNumber());
//			System.out.println("size : "+list.getSize());
//			System.out.println("totalElement : "+list.getTotalElements());
//			for(int j=0;j<list.getContent().size();j++) {
//				System.out.println(list.getContent().get(j).toString());
//			}
//		}
//		System.out.println();
//	}
	
	//검색할 때 마다 모두 DB에 저장한 뒤 추후 검색 시 select로 데이터 확인 후 insert하는 방법 처리 시간 확인
	//@Test
//	public void test1() {
//		//데이터 등록
//		for(int i=0; i<50000; i++) {
//			Place place = new Place();
//			place.setAddressName("주소");
//			place.setLatitude("위도");
//			place.setLongitude("경도");
//			place.setPhone("번호");
//			place.setPlaceName("장소명");
//			place.setPlaceUrl("URL");
//			place.setRoadAddressName("도로명주소");
//			if(i>10000) {
//				place.setUserId("javaboja5");
//			}else if(i>20000) {
//				place.setUserId("javaboja4");
//			}else if(i>30000) {
//				place.setUserId("javaboja3");
//			}else if(i>40000) {
//				place.setUserId("javaboja2");
//			}else{
//				place.setUserId("javaboja1");
//			}
//			
//			place.setPlaceCode(""+i);
//			em.persist(place);
//		}
//		//test2();
//		pr.deleteAllByUserId("javaboja1");
//		for(int i=0; i<10; i++) {
//			Place place = new Place();
//			place.setAddressName("주소");
//			place.setLatitude("위도");
//			place.setLongitude("경도");
//			place.setPhone("번호");
//			place.setPlaceName("장소명");
//			place.setPlaceUrl("URL");
//			place.setRoadAddressName("도로명주소");
//			place.setUserId("javaboja1");
//			place.setPlaceCode(""+i);
//			em.persist(place);
//		}
////		pr.findByPlaceCodeAndUserId("2342", "javaboja1");
////		pr.findByPlaceCodeAndUserId("223", "javaboja1");
////		pr.findByPlaceCodeAndUserId("12334", "javaboja1");
////		pr.findByPlaceCodeAndUserId("6654", "javaboja1");
////		pr.findByPlaceCodeAndUserId("5567", "javaboja1");
////		pr.findByPlaceCodeAndUserId("24", "javaboja1");
////		pr.findByPlaceCodeAndUserId("545", "javaboja1");
////		pr.findByPlaceCodeAndUserId("22222", "javaboja1");
////		pr.findByPlaceCodeAndUserId("34455", "javaboja1");
////		pr.findByPlaceCodeAndUserId("5678", "javaboja1");
//		
//	}
//	
//	//@Test
//	public void test2() {
//		//0.012ms
//		for(int i=0;i<10;i++) {
//			pr.findByPlaceCodeAndUserId("45434", "javaboja1");
//		}
//	}
//	
//	//@Test
//	public void test3() {
//		//
//		pr.deleteAllByUserId("javaboja1");
//	}
//	
//	@Test
//	public void test4() {
//		em.find(User.class, "javaboja1");
//	}
}
