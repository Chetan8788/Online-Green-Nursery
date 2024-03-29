package com.masai.service;

import java.util.List;

import com.masai.exception.PlantException;
import com.masai.exception.PlanterException;
import com.masai.exception.SeedException;
import com.masai.model.Planter;

public interface PlanterService {
	public Planter addPlanter(Planter planter) throws PlanterException;

	public Planter addSeedsInPlanter(Integer planterId, Integer seedId) throws PlanterException, SeedException;

	public Planter addPlantInPlanter(Integer planterId, Integer plantId) throws PlanterException, PlantException;

	public Planter updatePlanter(Planter planter) throws PlanterException;

	public Planter deletePlanter(Integer id) throws PlanterException;

	public Planter viewPlanter(Integer id) throws PlanterException;

	public List<Planter> viewPlanter(String plantrShape) throws PlanterException;

	public List<Planter> viewAllPlanters() throws PlanterException;

	public List<Planter> viewAllPlanters(Double minCost, Double maxCost) throws PlanterException;

}
