package com.javaboja.vo;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name="Place")
public class Place {
	@Id
	private String placeId;
	private String addressName;
	private String phone;
	private String placeName;
	private String placeUrl;
	private String roadAddressName;
	private String latitude;
	private String longitude;
	private String keyword;
}
