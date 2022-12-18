package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.auth.Authorization;
import com.masai.model.Plant;
import com.masai.service.PlantService;

@RestController
@RequestMapping(value = "plants")
public class PlantController {
	@Autowired
	PlantService plantService;
	@Autowired
	Authorization authorization;

	@PostMapping(value = "")
	public ResponseEntity<Plant> addPlant(@Valid @RequestBody Plant plant, @RequestHeader String token) {
		authorization.isAuthorized(token, "admin");

		return new ResponseEntity<Plant>(plantService.addPlant(plant), HttpStatus.CREATED);

	}

	@PutMapping(value = "")
	public ResponseEntity<Plant> updatePlant(@Valid @RequestBody Plant plant, @RequestHeader String token) {
		authorization.isAuthorized(token, "admin");
		return new ResponseEntity<Plant>(plantService.updatePlant(plant), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Plant> deletePlant(@PathVariable Integer id, @RequestHeader String token) {
		authorization.isAuthorized(token, "admin");
		return new ResponseEntity<Plant>(plantService.deletePlant(id), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Plant> viewPlant(@PathVariable Integer id) {
		return new ResponseEntity<Plant>(plantService.viewPlant(id), HttpStatus.OK);
	}

	@GetMapping(value = "by_name/{id}")
	public ResponseEntity<Plant> viewPlant(@PathVariable String commonName) {
		return new ResponseEntity<Plant>(plantService.viewPlant(commonName), HttpStatus.OK);
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Plant>> viewAllPlant() {
		return new ResponseEntity<List<Plant>>(plantService.viewAllPlants(), HttpStatus.OK);
	}

	@GetMapping(value = "/by_type/{id}")
	public ResponseEntity<List<Plant>> viewAllPlant(@PathVariable String typeOfPlant) {
		return new ResponseEntity<List<Plant>>(plantService.viewAllPlants(typeOfPlant), HttpStatus.OK);
	}

}
