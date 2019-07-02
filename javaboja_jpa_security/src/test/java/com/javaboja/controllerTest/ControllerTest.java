package com.javaboja.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javaboja.controller.LoginController;
import com.javaboja.controller.MainController;
import com.javaboja.vo.Place;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class ControllerTest{

	@Autowired
	private TestRestTemplate trt;
//	
//	@Before
//	public void setup() {
//		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
//	}

	@Test
	public void javabojaSearchTest() throws Exception {
		ResponseEntity<Place> data= trt.getForEntity("/main/place/search/keyword=네이버&url=https://dapi.kakao.com/v2/local/search/keyword.json?&pageSize=10&curPage=1&realSearch=true", Place.class);
		System.out.println(data.toString());
	}
}
