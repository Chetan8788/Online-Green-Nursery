package com.masai.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	static final String userType = "customer";
	@Size(max = 15,min = 4,message = "username should be of max 15 and min 4 characrter")
	private String name;
	@Column(unique = true)
	@Email(message = "Invalid formate of email")
	private String email;
	@Column(unique = true)
	@Size(max = 15,min = 4,message = "username should be of max 15 and min 4 characrter")
	private String username;
	@JsonIgnore
	@Min(value = 6)
	private String password;
	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Comment> comments = new ArrayList<>();
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<Address> address = new HashSet<>();
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();


}
