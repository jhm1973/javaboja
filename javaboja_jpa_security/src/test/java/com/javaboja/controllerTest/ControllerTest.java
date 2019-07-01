package com.javaboja.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javaboja.controller.LoginController;
import com.javaboja.controller.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest{

	private MockMvc mockMvc;
	
	@MockBean
	MainController mainController;
	
	@MockBean
	LoginController loginController;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	/*@Test
	public void loginTest() throws Exception {
		given(this.loginController.login("javaboja1"))).will
	}*/
}
