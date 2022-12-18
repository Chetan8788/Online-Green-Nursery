package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.PlantException;
import com.masai.model.Plant;
import com.masai.repository.PlantDao;

@Service
public class PlantServiceImpl implements PlantService {
	@Autowired
	PlantDao plantDao;

	@Override
	public Plant addPlant(Plant plant) throws PlantException {
		if (plant == null)
			throw new PlantException("Please, provide valid data");

		return plantDao.save(plant);
	}

	@Override
	public Plant updatePlant(Plant plant) throws PlantException {
		if (plant == null)
			throw new PlantException("Please, provide valid data");
		Plant foundPlant = plantDao.findById(plant.getId())
				.orElseThrow(() -> new PlantException("Plant does not exist"));
		return plantDao.save(plant);
	}

	@Override
	public Plant deletePlant(Integer id) throws PlantException {
		Plant foundPlant = plantDao.findById(id).orElseThrow(() -> new PlantException("Plant does not exist"));
		plantDao.delete(foundPlant);
		return foundPlant;
	}

	@Override
	public Plant viewPlant(Integer id) throws PlantException {

		Plant foundPlant = plantDao.findById(id).orElseThrow(() -> new PlantException("Plant does not exist"));
		return foundPlant;
	}

	@Override
	public Plant viewPlant(String commonName) throws PlantException {
		Plant foundPlant = plantDao.findByCommonName(commonName)
				.orElseThrow(() -> new PlantException("Plant does not exist"));
		return foundPlant;
	}

	@Override
	public List<Plant> viewAllPlants() throws PlantException {
		List<Plant> foundPlantList = plantDao.findAll();
		if (foundPlantList.isEmpty())
			throw new PlantException("There is no plant in the database");
		return foundPlantList;
	}

	@Override
	public List<Plant> viewAllPlants(String typeOfPlant) throws PlantException {
		List<Plant> foundPlantList = plantDao.findByType(typeOfPlant);
		if (foundPlantList.isEmpty())
			throw new PlantException("There is no plant in the database");
		return foundPlantList;
	}

}
