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
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.SeedException;
import com.masai.model.Seed;
import com.masai.service.SeedService;

@RestController
public class SeedController {
	@Autowired
	private SeedService seedService;

	@PostMapping("/seeds")
	public ResponseEntity<Seed> addSeed(@Valid @RequestBody Seed seed) throws SeedException {
		Seed registeredSeed = seedService.addSeed(seed);

		return new ResponseEntity<Seed>(registeredSeed, HttpStatus.CREATED);

	}

	@PutMapping("/seeds")
	public ResponseEntity<Seed> updateSeed(@Valid @RequestBody Seed seed) throws SeedException {
		Seed updatedSeed = seedService.updateSeed(seed);

		return new ResponseEntity<Seed>(updatedSeed, HttpStatus.OK);

	}

	@DeleteMapping("/seeds{seedId}")
	public ResponseEntity<Seed> deleteSeed(@PathVariable Integer seedId) throws SeedException {
		Seed deletedSeed = seedService.deleteSeed(seedId);

		return new ResponseEntity<Seed>(deletedSeed, HttpStatus.OK);

	}

	@GetMapping("/seeds{seedId}")
	public ResponseEntity<Seed> viewSeed(@PathVariable Integer seedId) throws SeedException {

		Seed seed = seedService.viewSeed(seedId);

		return new ResponseEntity<Seed>(seed, HttpStatus.OK);

	}

	@GetMapping("/getseeds{commonName}")
	public ResponseEntity<Seed> viewSeedByCommonName(@PathVariable("commonName") String commonName)
			throws SeedException {

		Seed seed = seedService.viewSeedByCommonName(commonName);

		return new ResponseEntity<Seed>(seed, HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<List<Seed>> viewAllSeeds() throws SeedException {

		List<Seed> seeds = seedService.viewAllSeeds();
		return new ResponseEntity<List<Seed>>(seeds, HttpStatus.OK);

	}

	@GetMapping("/getSeedsByType/{typeOfSeed}")
	public ResponseEntity<List<Seed>> viewAllSeedsByType(@PathVariable("typeOfSeed") String typeOfSeed)
			throws SeedException {

		List<Seed> seeds = seedService.viewAllSeedsByType(typeOfSeed);

		return new ResponseEntity<List<Seed>>(seeds, HttpStatus.OK);

	}

}
