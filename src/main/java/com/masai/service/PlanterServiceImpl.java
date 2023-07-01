package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.PlantException;
import com.masai.exception.PlanterException;
import com.masai.exception.SeedException;
import com.masai.model.Plant;
import com.masai.model.Planter;
import com.masai.model.Seed;
import com.masai.repository.PlantDao;
import com.masai.repository.PlanterDao;
import com.masai.repository.SeedDao;

@Service
public class PlanterServiceImpl implements PlanterService {
	@Autowired
	PlanterDao planterDao;
	@Autowired
	SeedDao seedDao;
	@Autowired
	PlantDao plantDao;

	/**
	 * Adds a new planter.
	 *
	 * @param planter The Planter object to be added.
	 * @return The created Planter object.
	 * @throws PlanterException if the provided planter object is null or if there
	 *                          is an error while adding the planter.
	 */
	@Override
	public Planter addPlanter(Planter planter) throws PlanterException {
		if (planter == null)
			throw new PlanterException("Please, provide valid data");

		return planterDao.save(planter);
	}

	/**
	 * Adds a seed to a planter.
	 *
	 * @param planterId The ID of the planter to which the seed should be added.
	 * @param seedId    The ID of the seed to be added.
	 * @return The updated Planter object with the added seed.
	 * @throws PlanterException if the planter with the given ID is not found or if
	 *                          there is an error while saving the planter.
	 * @throws SeedException    if the seed with the given ID is not found.
	 */
	@Override
	public Planter addSeedsInPlanter(Integer planterId, Integer seedId) throws PlanterException, SeedException {
		Seed seed = seedDao.findById(seedId).orElseThrow(() -> new SeedException("Seed not found"));
		Planter planter = planterDao.findById(planterId).orElseThrow(() -> new PlanterException("Planter not found"));
		planter.getSeeds().add(seed);
		seed.getPlanters().add(planter);

		return planterDao.save(planter);
	}

	/**
	 * Adds a plant to a planter.
	 *
	 * @param planterId The ID of the planter to which the plant should be added.
	 * @param plantId   The ID of the plant to be added.
	 * @return The updated Planter object with the added plant.
	 * @throws PlanterException if the planter with the given ID is not found or if
	 *                          there is an error while saving the planter.
	 * @throws PlantException   if the plant with the given ID is not found.
	 */
	@Override
	public Planter addPlantInPlanter(Integer planterId, Integer plantId) throws PlanterException, PlantException {
		Plant plant = plantDao.findById(plantId).orElseThrow(() -> new PlantException("Plant not found"));
		Planter planter = planterDao.findById(planterId).orElseThrow(() -> new PlanterException("Planter not found"));
		planter.getPlants().add(plant);
		plant.getPlanters().add(planter);

		return planterDao.save(planter);
	}

	/**
	 * Updates an existing planter.
	 *
	 * @param planter The Planter object containing the updated details.
	 * @return The updated Planter object.
	 * @throws PlanterException if the provided planter object is null or if the
	 *                          planter with the given ID does not exist, or if
	 *                          there is an error while saving the updated planter.
	 */
	@Override
	public Planter updatePlanter(Planter planter) throws PlanterException {
		if (planter == null)
			throw new PlanterException("Please, provide valid data");

		Planter foundPlanter = planterDao.findById(planter.getId())
				.orElseThrow(() -> new PlanterException("Plant does not exist"));

		return planterDao.save(planter);
	}

	/**
	 * Deletes a planter.
	 *
	 * @param id The ID of the planter to be deleted.
	 * @return The deleted Planter object.
	 * @throws PlanterException if the planter with the given ID does not exist or
	 *                          if there is an error while deleting the planter.
	 */
	@Override
	public Planter deletePlanter(Integer id) throws PlanterException {
		Planter foundPlanter = planterDao.findById(id).orElseThrow(() -> new PlanterException("Plant does not exist"));

		planterDao.delete(foundPlanter);
		return foundPlanter;
	}

	/**
	 * Retrieves a planter by ID.
	 *
	 * @param id The ID of the planter to be retrieved.
	 * @return The Planter object with the given ID.
	 * @throws PlanterException if the planter with the given ID does not exist.
	 */
	@Override
	public Planter viewPlanter(Integer id) throws PlanterException {
		Planter foundPlanter = planterDao.findById(id).orElseThrow(() -> new PlanterException("Plant does not exist"));

		return foundPlanter;
	}

	/**
	 * Retrieves a list of planters by shape.
	 *
	 * @param planterShape The shape of the planters to be retrieved.
	 * @return The list of planters with the given shape.
	 * @throws PlanterException if no planters with the given shape are found.
	 */
	@Override
	public List<Planter> viewPlanter(String planterShape) throws PlanterException {
		List<Planter> foundPlanterList = planterDao.findByShape(planterShape);
		if (foundPlanterList.isEmpty())
			throw new PlanterException("No record found in the database");

		return foundPlanterList;
	}

	/**
	 * Retrieves a list of planters within the specified cost range.
	 *
	 * @param minCost The minimum cost of the planters.
	 * @param maxCost The maximum cost of the planters.
	 * @return The list of planters within the specified cost range.
	 * @throws PlanterException if no planters within the specified cost range are
	 *                          found.
	 */
	@Override
	public List<Planter> viewAllPlanters(Double minCost, Double maxCost) throws PlanterException {
		List<Planter> foundPlanterList = planterDao.findByCostBetweenOrderByCost(minCost, maxCost);
		if (foundPlanterList.isEmpty())
			throw new PlanterException("No record found in the database");

		return foundPlanterList;
	}

	/**
	 * Retrieves a list of all planters.
	 *
	 * @return The list of all planters.
	 * @throws PlanterException if no planters are found in the database.
	 */
	@Override
	public List<Planter> viewAllPlanters() throws PlanterException {
		List<Planter> foundPlanterList = planterDao.findAll();
		if (foundPlanterList.isEmpty())
			throw new PlanterException("No record found in the database");

		return foundPlanterList;
	}

}
