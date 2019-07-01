package com.javaboja.vo;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name="Place")
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int placeId;
	private String addressName;
	private String phone;
	private String placeName;
	private String placeUrl;
	private String roadAddressName;
	private String latitude;
	private String longitude;
	private String keyword;
	
	private String userId;
}
