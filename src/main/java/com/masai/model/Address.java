package com.masai.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "addresses")
@EqualsAndHashCode(exclude = { "id", "customer" })
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String houseNo;
	private String colony;
	private String pincode;
	private String city;
	private String state;
	private String country;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Customer> customer = new HashSet<>();
}
