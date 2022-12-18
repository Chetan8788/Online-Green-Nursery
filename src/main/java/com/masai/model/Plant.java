package com.masai.model;

import java.util.HashSet;
import java.util.Objects;
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

@Data
@Entity
@Table(name = "plants")
public class Plant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double height;
	private String spread;
	private String commonName;
	private String bloomTime;
	private String medicinalOrCulinaryUse;
	private String difficultyLevel;
	private Float temperature;
	private String type;
	private String desciption;
	private Integer stock;
	private Double cost;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "plants")
	private Set<Planter> planters = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plant other = (Plant) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
