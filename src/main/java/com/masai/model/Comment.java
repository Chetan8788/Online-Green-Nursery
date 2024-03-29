package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime timeStamp;
	private String commentString;
	@Min(0)
	@Max(5)
	private Float rating;
	@ManyToOne(cascade = CascadeType.ALL)
	private Planter planter;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

}
