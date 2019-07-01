package com.javaboja.vo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

	@Id
	private String userId;
	private String userPassword;
	
	@OneToMany
	@JoinColumn(name="userId")
	private List<History> history;
}
