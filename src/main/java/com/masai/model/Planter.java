package com.masai.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "planters")
public class Planter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Float height;
	private Integer capacity;
	private Integer drainageHoles;
	private String color;
	private String shape;
	private Integer stock;
	private Double cost;
	private Float rating;
	private Float numberOfRatings;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "planter")
	private List<Comment> comments = new ArrayList<>();
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Plant> plants = new HashSet<>();
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Seed> seeds = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planter other = (Planter) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
