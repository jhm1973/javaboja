package com.javaboja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaboja.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.javaboja.vo.User user = userRepository.findByUserId(userId);
		
		if(user == null) {
			
			throw new UsernameNotFoundException(userId);
		}
		return new User(user.getUserId(),user.getUserPassword(), AuthorityUtils.createAuthorityList("USER"));
	}
}
