package com.javaboja.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="History")

public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long historyId;
	private String keyword;

	@CreationTimestamp
	private LocalDateTime createDateTime;
	private String userId;
	
	@Transient
	private int views;
}
