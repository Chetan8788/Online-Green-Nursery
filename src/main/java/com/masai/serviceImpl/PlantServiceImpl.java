
package com.masai.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.PlantException;
import com.masai.model.Plant;
import com.masai.repository.PlantDao;
import com.masai.service.PlantService;

@Service
public class PlantServiceImpl implements PlantService {

	@Autowired
	PlantDao plantDao;

	/**
	 * Adds a new plant.
	 *
	 * @param plant The Plant object to be added.
	 * @return The created Plant object.
	 * @throws PlantException if the provided plant object is null.
	 */
	@Override
	public Plant addPlant(Plant plant) throws PlantException {
		if (plant == null) {
			throw new PlantException("Please provide valid data for the plant.");
		}
		return plantDao.save(plant);
	}

	/**
	 * Updates an existing plant.
	 *
	 * @param plant The updated Plant object.
	 * @return The updated Plant object.
	 * @throws PlantException if the provided plant object is null or if the plant
	 *                        does not exist.
	 */
	@Override
	public Plant updatePlant(Plant plant) throws PlantException {
		if (plant == null) {
			throw new PlantException("Please provide valid data for the plant.");
		}
		Plant foundPlant = plantDao.findById(plant.getId())
				.orElseThrow(() -> new PlantException("Plant does not exist"));
		return plantDao.save(plant);
	}

	/**
	 * Deletes a plant.
	 *
	 * @param id The ID of the plant to be deleted.
	 * @return The deleted Plant object.
	 * @throws PlantException if the plant with the given ID does not exist or if
	 *                        there is an error while deleting the plant.
	 */
	@Override
	public Plant deletePlant(Integer id) throws PlantException {
		Plant foundPlant = plantDao.findById(id).orElseThrow(() -> new PlantException("Plant does not exist"));
		plantDao.delete(foundPlant);
		return foundPlant;
	}

	/**
	 * Retrieves a plant by ID.
	 *
	 * @param id The ID of the plant to be retrieved.
	 * @return The Plant object with the given ID.
	 * @throws PlantException if the plant with the given ID does not exist.
	 */
	@Override
	public Plant viewPlant(Integer id) throws PlantException {
		Plant foundPlant = plantDao.findById(id).orElseThrow(() -> new PlantException("Plant does not exist"));
		return foundPlant;
	}

	/**
	 * Retrieves a plant by common name.
	 *
	 * @param commonName The common name of the plant to be retrieved.
	 * @return The Plant object with the given common name.
	 * @throws PlantException if the plant with the given common name does not
	 *                        exist.
	 */
	@Override
	public Plant viewPlant(String commonName) throws PlantException {
		Plant foundPlant = plantDao.findByCommonName(commonName)
				.orElseThrow(() -> new PlantException("Plant does not exist by the given name"));
		return foundPlant;
	}

	/**
	 * Retrieves a list of all plants.
	 *
	 * @return The list of all plants.
	 * @throws PlantException if no plants are found in the database.
	 */
	@Override
	public List<Plant> viewAllPlants() throws PlantException {
		List<Plant> foundPlantList = plantDao.findAll();
		if (foundPlantList.isEmpty()) {
			throw new PlantException("There is no plant in the database");
		}
		return foundPlantList;
	}

	/**
	 * Retrieves a list of plants by type.
	 *
	 * @param typeOfPlant The type of plants to be retrieved.
	 * @return The list of plants with the given type.
	 * @throws PlantException if no plants with the given type are found.
	 */
	@Override
	public List<Plant> viewAllPlants(String typeOfPlant) throws PlantException {
		List<Plant> foundPlantList = plantDao.findByType(typeOfPlant);
		if (foundPlantList.isEmpty()) {
			throw new PlantException("There is no plant in the database");
		}
		return foundPlantList;
	}
}
