package com.javaboja.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.javaboja.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	public String login(Principal principal) {
		if(principal!=null) {
			return "redirect:main";
		}else {
			return "login";
		}
	}
	
}
