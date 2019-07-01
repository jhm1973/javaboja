package com.javaboja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.javaboja.repository.UserRepository;
import com.javaboja.vo.User;

@SpringBootApplication
public class JavabojaJpaSecurityApplication {

	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(JavabojaJpaSecurityApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	      return new BCryptPasswordEncoder();
	}
	
	//임시 아이디 생성
	@Bean
	public void userInsert() {
		for(int i=0;i<10;i++) {
			User user = new User();
			user.setUserId("javaboja"+i);
			user.setUserPassword(bCryptPasswordEncoder().encode("1234"));
			userRepository.save(user);
		}
		
	}
}
