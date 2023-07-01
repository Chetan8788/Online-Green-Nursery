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

@RestController
@RequestMapping(value = "plants")
@CrossOrigin("*")
public class PlantController {
	private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

	@Autowired
	private PlantService plantService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "")
	public ResponseEntity<Plant> addPlant(@Valid @RequestBody Plant plant) {
		logger.info("Adding a new plant: {}", plant);
		Plant addedPlant = plantService.addPlant(plant);
		logger.info("Plant added: {}", addedPlant);
		return new ResponseEntity<>(addedPlant, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "")
	public ResponseEntity<Plant> updatePlant(@Valid @RequestBody Plant plant) {
		logger.info("Updating plant: {}", plant);
		Plant updatedPlant = plantService.updatePlant(plant);
		logger.info("Plant updated: {}", updatedPlant);
		return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Plant> deletePlant(@PathVariable Integer id) {
		logger.info("Deleting plant with ID: {}", id);
		Plant deletedPlant = plantService.deletePlant(id);
		logger.info("Plant deleted: {}", deletedPlant);
		return new ResponseEntity<>(deletedPlant, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Plant> viewPlant(@PathVariable Integer id) {
		logger.info("Viewing plant with ID: {}", id);
		Plant plant = plantService.viewPlant(id);
		logger.info("Plant retrieved: {}", plant);
		return new ResponseEntity<>(plant, HttpStatus.OK);
	}

	@GetMapping(value = "by_name/{commonName}")
	public ResponseEntity<Plant> viewPlantByName(@PathVariable String commonName) {
		logger.info("Viewing plant with common name: {}", commonName);
		Plant plant = plantService.viewPlant(commonName);
		logger.info("Plant retrieved: {}", plant);
		return new ResponseEntity<>(plant, HttpStatus.OK);
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Plant>> viewAllPlants() {
		logger.info("Viewing all plants");
		List<Plant> plants = plantService.viewAllPlants();
		logger.info("Total plants retrieved: {}", plants.size());
		return new ResponseEntity<>(plants, HttpStatus.OK);
	}

	@GetMapping(value = "/by_type/{typeOfPlant}")
	public ResponseEntity<List<Plant>> viewAllPlantsByType(@PathVariable String typeOfPlant) {
		logger.info("Viewing plants of type: {}", typeOfPlant);
		List<Plant> plants = plantService.viewAllPlants(typeOfPlant);
		logger.info("Total plants retrieved: {}", plants.size());
		return new ResponseEntity<>(plants, HttpStatus.OK);
	}
}
