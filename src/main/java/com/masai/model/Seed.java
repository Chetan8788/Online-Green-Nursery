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
@Table(name = "seeds")
public class Seed {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer seedId;
	private String commonName;
	private String watering;
	private String difficultyLevel;
	private String temperature;
	private String type;
	private String desciption;
	private Integer stock;
	private Double cost;
	private Integer seedsPerPacket;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "seeds")
	private Set<Planter> planters = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seed other = (Seed) obj;
		return Objects.equals(commonName, other.commonName) && Objects.equals(cost, other.cost)
				&& Objects.equals(desciption, other.desciption)
				&& Objects.equals(difficultyLevel, other.difficultyLevel)
				&& Objects.equals(seedsPerPacket, other.seedsPerPacket) && Objects.equals(stock, other.stock)
				&& Objects.equals(temperature, other.temperature) && Objects.equals(type, other.type)
				&& Objects.equals(watering, other.watering);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commonName, cost, desciption, difficultyLevel, seedsPerPacket, stock, temperature, type,
				watering);
	}
	
	

	
}
