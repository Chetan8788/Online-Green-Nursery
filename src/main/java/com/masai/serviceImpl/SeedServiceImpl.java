package com.masai.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.SeedException;
import com.masai.model.Planter;
import com.masai.model.Seed;
import com.masai.repository.SeedDao;
import com.masai.service.SeedService;

@Service
public class SeedServiceImpl implements SeedService {

	@Autowired
	private SeedDao seedDao;

	/**
	 * Adds a new seed.
	 *
	 * @param seed The Seed object to be added.
	 * @return The created Seed object.
	 * @throws SeedException if the provided seed object is null or if the seed
	 *                       couldn't be added.
	 */
	@Override
	public Seed addSeed(Seed seed) throws SeedException {
		if (seed == null) {
			throw new SeedException("Please provide valid data for the seed.");
		}

		Set<Planter> planters = seed.getPlanters();
		for (Planter planter : planters) {
			planter.getSeeds().add(seed);
		}

		Seed registeredSeed = seedDao.save(seed);
		if (registeredSeed != null) {
			return registeredSeed;
		} else {
			throw new SeedException("Seed couldn't be added.");
		}
	}

	/**
	 * Updates an existing seed.
	 *
	 * @param seed The updated Seed object.
	 * @return The existing Seed object before the update.
	 * @throws SeedException if the provided seed object is null or if the seed with
	 *                       the given seedId is not found.
	 */
	@Override
	public Seed updateSeed(Seed seed) throws SeedException {
		Optional<Seed> optional = seedDao.findById(seed.getSeedId());
		if (optional.isPresent()) {
			Seed existingSeed = optional.get();
			seedDao.save(seed);
			return existingSeed;
		} else {
			throw new SeedException("Seed not found with seedId: " + seed.getSeedId());
		}
	}

	/**
	 * Deletes a seed.
	 *
	 * @param seedId The ID of the seed to be deleted.
	 * @return The deleted Seed object.
	 * @throws SeedException if the seed with the given seedId is not found or if
	 *                       there is an error while deleting the seed.
	 */
	@Override
	public Seed deleteSeed(Integer seedId) throws SeedException {
		Optional<Seed> optional = seedDao.findById(seedId);

		if (optional.isPresent()) {
			seedDao.deleteById(seedId);
			return optional.get();
		} else {
			throw new SeedException("Seed not found with seedId: " + seedId);
		}
	}

	/**
	 * Retrieves a seed by ID.
	 *
	 * @param seedId The ID of the seed to be retrieved.
	 * @return The Seed object with the given ID.
	 * @throws SeedException if the seed with the given seedId is not found.
	 */
	@Override
	public Seed viewSeed(Integer seedId) throws SeedException {
		Optional<Seed> optional = seedDao.findById(seedId);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new SeedException("Seed not found with seedId: " + seedId);
		}
	}

	/**
	 * Retrieves a seed by common name.
	 *
	 * @param commonName The common name of the seed to be retrieved.
	 * @return The Seed object with the given common name.
	 * @throws SeedException if the seed with the given common name is not found.
	 */
	@Override
	public Seed viewSeedByCommonName(String commonName) throws SeedException {
		Seed seed = seedDao.findByCommonName(commonName);
		if (seed != null) {
			return seed;
		} else {
			throw new SeedException("Seed not found with common name: " + commonName);
		}
	}

	/**
	 * Retrieves all seeds.
	 *
	 * @return A list of all Seed objects.
	 * @throws SeedException if there are no seeds found.
	 */
	@Override
	public List<Seed> viewAllSeeds() throws SeedException {
		List<Seed> seeds = seedDao.findAll();
		if (seeds != null) {
			return seeds;
		} else {
			throw new SeedException("Seeds not found.");
		}
	}

	/**
	 * Retrieves all seeds of a specific type.
	 *
	 * @param typeOfSeed The type of seed to be retrieved.
	 * @return A list of Seed objects of the specified type.
	 * @throws SeedException if there are no seeds found of the specified type.
	 */
	@Override
	public List<Seed> viewAllSeedsByType(String typeOfSeed) throws SeedException {
		List<Seed> seeds = seedDao.findByType(typeOfSeed);
		if (seeds != null) {
			return seeds;
		} else {
			throw new SeedException("Seeds not found.");
		}
	}
}
