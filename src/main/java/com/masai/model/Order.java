package com.masai.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer OrderId;
	private LocalDateTime orderDate;
	private Integer quantity;
	private Double totalCost;
	private String transactionMode;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Planter> planters = new ArrayList<>();
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;
}
