package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.model.Plant;
import com.masai.service.PlantService;

/**
 * The PlantController class handles CRUD operations for plants. It provides
 * methods for adding a new plant, updating an existing plant, deleting a plant,
 * and fetching plants by their ID, name, or type.
 */
@RestController
@RequestMapping(value = "plants")
@CrossOrigin("*")
public class PlantController {

	private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

	@Autowired
	private PlantService plantService;

	/**
	 * Adds a new plant.
	 *
	 * @param plant The Plant object containing the plant details.
	 * @return ResponseEntity containing the created Plant object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Plant> addPlant(@Valid @RequestBody Plant plant) {
		logger.info("Adding a new plant: {}", plant);
		Plant addedPlant = plantService.addPlant(plant);
		logger.info("Plant added: {}", addedPlant);
		return new ResponseEntity<>(addedPlant, HttpStatus.CREATED);
	}

	/**
	 * Updates an existing plant.
	 *
	 * @param plant The Plant object containing the updated plant details.
	 * @return ResponseEntity containing the updated Plant object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<Plant> updatePlant(@Valid @RequestBody Plant plant) {
		logger.info("Updating plant: {}", plant);
		Plant updatedPlant = plantService.updatePlant(plant);
		logger.info("Plant updated: {}", updatedPlant);
		return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
	}

	/**
	 * Deletes a plant by its ID.
	 *
	 * @param plantId The ID of the plant to delete.
	 * @return ResponseEntity containing the deleted Plant object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{plantId}")
	public ResponseEntity<Plant> deletePlant(@PathVariable("plantId") Integer plantId) {
		logger.info("Deleting plant with ID: {}", plantId);
		Plant deletedPlant = plantService.deletePlant(plantId);
		logger.info("Plant deleted: {}", deletedPlant);
		return new ResponseEntity<>(deletedPlant, HttpStatus.OK);
	}

	/**
	 * Fetches a plant by its ID.
	 *
	 * @param plantId The ID of the plant to fetch.
	 * @return ResponseEntity containing the fetched Plant object.
	 */
	@GetMapping(value = "/{plantId}")
	public ResponseEntity<Plant> viewPlant(@PathVariable("plantId") Integer plantId) {
		logger.info("Viewing plant with ID: {}", plantId);
		Plant plant = plantService.viewPlant(plantId);
		logger.info("Plant retrieved: {}", plant);
		return new ResponseEntity<>(plant, HttpStatus.OK);
	}

	/**
	 * Fetches a plant by its common name.
	 *
	 * @param commonName The common name of the plant to fetch.
	 * @return ResponseEntity containing the fetched Plant object.
	 */
	@GetMapping(value = "name/{commonName}")
	public ResponseEntity<Plant> viewPlantByName(@PathVariable String commonName) {
		logger.info("Viewing plant with common name: {}", commonName);
		Plant plant = plantService.viewPlant(commonName);
		logger.info("Plant retrieved: {}", plant);
		return new ResponseEntity<>(plant, HttpStatus.OK);
	}

	/**
	 * Fetches all plants.
	 *
	 * @return ResponseEntity containing the list of all plants.
	 */
	@GetMapping
	public ResponseEntity<List<Plant>> viewAllPlants() {
		logger.info("Viewing all plants");
		List<Plant> plants = plantService.viewAllPlants();
		logger.info("Total plants retrieved: {}", plants.size());
		return new ResponseEntity<>(plants, HttpStatus.OK);
	}

	/**
	 * Fetches all plants of a specific type.
	 *
	 * @param typeOfPlant The type of plants to fetch.
	 * @return ResponseEntity containing the list of plants of the specified type.
	 */
	@GetMapping(value = "/type/{typeOfPlant}")
	public ResponseEntity<List<Plant>> viewAllPlantsByType(@PathVariable String typeOfPlant) {
		logger.info("Viewing plants of type: {}", typeOfPlant);
		List<Plant> plants = plantService.viewAllPlants(typeOfPlant);
		logger.info("Total plants retrieved: {}", plants.size());
		return new ResponseEntity<>(plants, HttpStatus.OK);
	}
}
