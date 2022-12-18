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

	@Override
	public Planter addPlanter(Planter planter) throws PlanterException {
		if (planter == null)
			throw new PlanterException("Please, provide valid data");

		return planterDao.save(planter);
	}

	@Override
	public String addSeedsInPlanter(Integer planterId, Integer seedId) throws PlanterException, SeedException {
		Seed seed = seedDao.findById(seedId).orElseThrow(() -> new SeedException("Seed not found"));
		Planter planter = planterDao.findById(planterId).orElseThrow(() -> new PlanterException("Planter not found"));
		planter.getSeeds().add(seed);
		seed.getPlanters().add(planter);
		planterDao.save(planter);
		return "Done...";
	}

	@Override
	public String addPlantInPlanter(Integer planterId, Integer plantId) throws PlanterException, PlantException {

		Plant plant = plantDao.findById(plantId).orElseThrow(() -> new PlantException("Plant not found"));
		Planter planter = planterDao.findById(planterId).orElseThrow(() -> new PlanterException("Planter not found"));
		planter.getPlants().add(plant);
		plant.getPlanters().add(planter);

		planterDao.save(planter);
		return "Done...";
	}

	@Override
	public Planter updatePlanter(Planter planter) throws PlanterException {
		if (planter == null)
			throw new PlanterException("Please, provide valid data");
		Planter foundPlanter = planterDao.findById(planter.getId())
				.orElseThrow(() -> new PlanterException("Plant does not exist"));
		return planterDao.save(planter);
	}

	@Override
	public Planter deletePlanter(Integer id) throws PlanterException {
		Planter foundPlant = planterDao.findById(id).orElseThrow(() -> new PlanterException("Plant does not exist"));
		planterDao.delete(foundPlant);
		return foundPlant;
	}

	@Override
	public Planter viewPlanter(Integer id) throws PlanterException {
		Planter foundPlanter = planterDao.findById(id).orElseThrow(() -> new PlanterException("Plant does not exist"));
		return foundPlanter;
	}

	@Override
	public List<Planter> viewPlanter(String plantrShape) throws PlanterException {
		List<Planter> foundPlanterList = planterDao.findByShape(plantrShape);
		if (foundPlanterList.isEmpty())
			throw new PlanterException("No record found in the database");
		return foundPlanterList;
	}

	@Override
	public List<Planter> viewAllPlanters() throws PlanterException {
		List<Planter> foundPlanterList = planterDao.findAll();
		if (foundPlanterList.isEmpty())
			throw new PlanterException("No record found in the database");
		return foundPlanterList;
	}

	@Override
	public List<Planter> viewAllPlanters(Double minCost, Double maxCost) throws PlanterException {
		List<Planter> foundPlanterList = planterDao.findByCostBetweenOrderByCost(minCost, maxCost);
		if (foundPlanterList.isEmpty())
			throw new PlanterException("No record found in the database");
		return foundPlanterList;
	}

}
