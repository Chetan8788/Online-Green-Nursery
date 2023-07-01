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

import com.masai.exception.SeedException;
import com.masai.model.Seed;
import com.masai.service.SeedService;

/**
 * The SeedController class handles CRUD operations for seeds. It provides
 * methods for adding a new seed, updating an existing seed, deleting a seed,
 * and fetching seeds by their ID, common name, type, or all seeds.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/seeds")
public class SeedController {

	private static final Logger logger = LoggerFactory.getLogger(SeedController.class);

	@Autowired
	private SeedService seedService;

	/**
	 * Adds a new seed.
	 *
	 * @param seed The Seed object containing the seed details.
	 * @return ResponseEntity containing the created Seed object.
	 * @throws SeedException if there is an error while adding the seed.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Seed> addSeed(@Valid @RequestBody Seed seed) throws SeedException {
		logger.info("Adding a new seed: {}", seed);
		Seed registeredSeed = seedService.addSeed(seed);
		logger.info("Seed added successfully: {}", registeredSeed);
		return new ResponseEntity<>(registeredSeed, HttpStatus.CREATED);
	}

	/**
	 * Updates an existing seed.
	 *
	 * @param seed The Seed object containing the updated seed details.
	 * @return ResponseEntity containing the updated Seed object.
	 * @throws SeedException if there is an error while updating the seed.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<Seed> updateSeed(@Valid @RequestBody Seed seed) throws SeedException {
		logger.info("Updating seed: {}", seed);
		Seed updatedSeed = seedService.updateSeed(seed);
		logger.info("Seed updated successfully: {}", updatedSeed);
		return new ResponseEntity<>(updatedSeed, HttpStatus.OK);
	}

	/**
	 * Deletes a seed.
	 *
	 * @param seedId The ID of the seed to delete.
	 * @return ResponseEntity containing the deleted Seed object.
	 * @throws SeedException if there is an error while deleting the seed.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{seedId}")
	public ResponseEntity<Seed> deleteSeed(@PathVariable("seedId") Integer seedId) throws SeedException {
		logger.info("Deleting seed with ID: {}", seedId);
		Seed deletedSeed = seedService.deleteSeed(seedId);
		logger.info("Seed deleted successfully: {}", deletedSeed);
		return new ResponseEntity<>(deletedSeed, HttpStatus.OK);
	}

	/**
	 * Fetches a seed by ID.
	 *
	 * @param seedId The ID of the seed to fetch.
	 * @return ResponseEntity containing the retrieved Seed object.
	 * @throws SeedException if there is an error while fetching the seed.
	 */
	@GetMapping("/{seedId}")
	public ResponseEntity<Seed> viewSeedById(@PathVariable Integer seedId) throws SeedException {
		logger.info("Fetching seed with ID: {}", seedId);
		Seed seed = seedService.viewSeed(seedId);
		logger.info("Seed fetched successfully: {}", seed);
		return new ResponseEntity<>(seed, HttpStatus.OK);
	}

	/**
	 * Fetches a seed by common name.
	 *
	 * @param commonName The common name of the seed to fetch.
	 * @return ResponseEntity containing the retrieved Seed object.
	 * @throws SeedException if there is an error while fetching the seed.
	 */
	@GetMapping("/name/{commonName}")
	public ResponseEntity<Seed> viewSeedByCommonName(@PathVariable("commonName") String commonName)
			throws SeedException {
		logger.info("Fetching seed with common name: {}", commonName);
		Seed seed = seedService.viewSeedByCommonName(commonName);
		logger.info("Seed fetched successfully: {}", seed);
		return new ResponseEntity<>(seed, HttpStatus.OK);
	}

	/**
	 * Fetches all seeds.
	 *
	 * @return ResponseEntity containing the list of all seeds.
	 * @throws SeedException if there is an error while fetching the seeds.
	 */
	@GetMapping
	public ResponseEntity<List<Seed>> viewAllSeeds() throws SeedException {
		logger.info("Fetching all seeds");
		List<Seed> seeds = seedService.viewAllSeeds();
		logger.info("Seeds fetched successfully. Count: {}", seeds.size());
		return new ResponseEntity<>(seeds, HttpStatus.OK);
	}

	/**
	 * Fetches seeds by type.
	 *
	 * @param typeOfSeed The type of the seeds to fetch.
	 * @return ResponseEntity containing the list of seeds with the specified type.
	 * @throws SeedException if there is an error while fetching the seeds.
	 */
	@GetMapping("/type/{typeOfSeed}")
	public ResponseEntity<List<Seed>> viewAllSeedsByType(@PathVariable("typeOfSeed") String typeOfSeed)
			throws SeedException {
		logger.info("Fetching seeds by type: {}", typeOfSeed);
		List<Seed> seeds = seedService.viewAllSeedsByType(typeOfSeed);
		logger.info("Seeds fetched successfully. Count: {}", seeds.size());
		return new ResponseEntity<>(seeds, HttpStatus.OK);
	}
}
