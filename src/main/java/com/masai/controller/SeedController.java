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
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.SeedException;
import com.masai.model.Seed;
import com.masai.service.SeedService;

@RestController
@CrossOrigin("*")
public class SeedController {
	private static final Logger logger = LoggerFactory.getLogger(SeedController.class);

	@Autowired
	private SeedService seedService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/seeds")
	public ResponseEntity<Seed> addSeed(@Valid @RequestBody Seed seed) throws SeedException {
		logger.info("Adding a new seed: {}", seed);
		Seed registeredSeed = seedService.addSeed(seed);
		logger.info("Seed added successfully: {}", registeredSeed);
		return new ResponseEntity<>(registeredSeed, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/seeds")
	public ResponseEntity<Seed> updateSeed(@Valid @RequestBody Seed seed) throws SeedException {
		logger.info("Updating seed: {}", seed);
		Seed updatedSeed = seedService.updateSeed(seed);
		logger.info("Seed updated successfully: {}", updatedSeed);
		return new ResponseEntity<>(updatedSeed, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/seeds/{seedId}")
	public ResponseEntity<Seed> deleteSeed(@PathVariable Integer seedId) throws SeedException {
		logger.info("Deleting seed with ID: {}", seedId);
		Seed deletedSeed = seedService.deleteSeed(seedId);
		logger.info("Seed deleted successfully: {}", deletedSeed);
		return new ResponseEntity<>(deletedSeed, HttpStatus.OK);
	}

	@GetMapping("/seeds/{seedId}")
	public ResponseEntity<Seed> viewSeed(@PathVariable Integer seedId) throws SeedException {
		logger.info("Fetching seed with ID: {}", seedId);
		Seed seed = seedService.viewSeed(seedId);
		logger.info("Seed fetched successfully: {}", seed);
		return new ResponseEntity<>(seed, HttpStatus.OK);
	}

	@GetMapping("/getseeds/{commonName}")
	public ResponseEntity<Seed> viewSeedByCommonName(@PathVariable("commonName") String commonName)
			throws SeedException {
		logger.info("Fetching seed with common name: {}", commonName);
		Seed seed = seedService.viewSeedByCommonName(commonName);
		logger.info("Seed fetched successfully: {}", seed);
		return new ResponseEntity<>(seed, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Seed>> viewAllSeeds() throws SeedException {
		logger.info("Fetching all seeds");
		List<Seed> seeds = seedService.viewAllSeeds();
		logger.info("Seeds fetched successfully. Count: {}", seeds.size());
		return new ResponseEntity<>(seeds, HttpStatus.OK);
	}

	@GetMapping("/getSeedsByType/{typeOfSeed}")
	public ResponseEntity<List<Seed>> viewAllSeedsByType(@PathVariable("typeOfSeed") String typeOfSeed)
			throws SeedException {
		logger.info("Fetching seeds by type: {}", typeOfSeed);
		List<Seed> seeds = seedService.viewAllSeedsByType(typeOfSeed);
		logger.info("Seeds fetched successfully. Count: {}", seeds.size());
		return new ResponseEntity<>(seeds, HttpStatus.OK);
	}
}
