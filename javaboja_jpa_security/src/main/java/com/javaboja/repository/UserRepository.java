package com.javaboja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaboja.vo.User;

public interface UserRepository extends JpaRepository<User, String>{

	public User findByUserId(String id);
}
