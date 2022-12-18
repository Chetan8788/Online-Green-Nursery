package com.masai.service;

import java.util.List;

import com.masai.exception.PlantException;
import com.masai.model.Plant;

public interface PlantService {
	public Plant addPlant(Plant plant) throws PlantException;

	public Plant updatePlant(Plant plant) throws PlantException;

	public Plant deletePlant(Integer id) throws PlantException;

	public Plant viewPlant(Integer id) throws PlantException;

	public Plant viewPlant(String commonName) throws PlantException;

	public List<Plant> viewAllPlants() throws PlantException;

	public List<Plant> viewAllPlants(String typeOfPlant) throws PlantException;
}
