package com.masai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Plant;

@Repository
public interface PlantDao extends JpaRepository<Plant, Integer> {

	Optional<Plant> findByCommonName(String commonName);

	List<Plant> findByType(String typeOfPlant);

}
