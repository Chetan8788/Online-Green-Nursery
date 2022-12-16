package com.masai.model;

import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "addresses")
public class Address {
	
	private Integer id;
	private String houseNo;
	private String colony;
	private String pincode;
	private String city;
	private String state;
	private String country;
	
}
