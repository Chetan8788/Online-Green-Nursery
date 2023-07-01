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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.model.Planter;
import com.masai.service.PlanterService;

/**
 * The PlanterController class handles CRUD operations for planters. It provides
 * methods for adding a new planter, updating an existing planter, deleting a
 * planter, and fetching planters by their ID, shape, or cost range.
 */
@RestController
@RequestMapping(value = "planters")
@CrossOrigin("*")
public class PlanterController {

	private static final Logger logger = LoggerFactory.getLogger(PlanterController.class);

	@Autowired
	private PlanterService planterService;

	/**
	 * Adds a new planter.
	 *
	 * @param planter The Planter object containing the planter details.
	 * @return ResponseEntity containing the created Planter object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "")
	public ResponseEntity<Planter> addPlanter(@Valid @RequestBody Planter planter) {
		logger.info("Adding planter: {}", planter);
		return new ResponseEntity<>(planterService.addPlanter(planter), HttpStatus.CREATED);
	}

	/**
	 * Adds seeds to a planter.
	 *
	 * @param planterId The ID of the planter.
	 * @param seedId    The ID of the seed to add.
	 * @return ResponseEntity containing the updated Planter object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = "seeds")
	public ResponseEntity<Planter> addSeedsInPlanter(@RequestParam Integer planterId, @RequestParam Integer seedId) {
		logger.info("Adding seeds to planter: planterId={}, seedId={}", planterId, seedId);
		return new ResponseEntity<>(planterService.addSeedsInPlanter(planterId, seedId), HttpStatus.CREATED);
	}

	/**
	 * Adds a plant to a planter.
	 *
	 * @param planterId The ID of the planter.
	 * @param plantId   The ID of the plant to add.
	 * @return ResponseEntity containing the updated Planter object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = "plants")
	public ResponseEntity<Planter> addPlantInPlanter(@RequestParam Integer planterId, @RequestParam Integer plantId) {
		logger.info("Adding plant to planter: planterId={}, plantId={}", planterId, plantId);
		return new ResponseEntity<>(planterService.addPlantInPlanter(planterId, plantId), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing planter.
	 *
	 * @param planter The Planter object containing the updated planter details.
	 * @return ResponseEntity containing the updated Planter object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "")
	public ResponseEntity<Planter> updatePlanter(@Valid @RequestBody Planter planter) {
		logger.info("Updating planter: {}", planter);
		return new ResponseEntity<>(planterService.updatePlanter(planter), HttpStatus.OK);
	}

	/**
	 * Deletes a planter.
	 *
	 * @param id The ID of the planter to delete.
	 * @return ResponseEntity containing the deleted Planter object.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Planter> deletePlanter(@PathVariable Integer id) {
		logger.info("Deleting planter: id={}", id);
		return new ResponseEntity<>(planterService.deletePlanter(id), HttpStatus.OK);
	}

	/**
	 * Fetches a planter by ID.
	 *
	 * @param id The ID of the planter to fetch.
	 * @return ResponseEntity containing the retrieved Planter object.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Planter> viewPlanter(@PathVariable Integer id) {
		logger.info("Viewing planter: id={}", id);
		return new ResponseEntity<>(planterService.viewPlanter(id), HttpStatus.OK);
	}

	/**
	 * Fetches planters by shape.
	 *
	 * @param planterShape The shape of the planters to fetch.
	 * @return ResponseEntity containing the list of planters with the specified
	 *         shape.
	 */
	@GetMapping(value = "byShape/{planterShape}")
	public ResponseEntity<List<Planter>> viewPlanterByShape(@PathVariable String planterShape) {
		logger.info("Viewing planters by shape: planterShape={}", planterShape);
		return new ResponseEntity<>(planterService.viewPlanter(planterShape), HttpStatus.OK);
	}

	/**
	 * Fetches all planters.
	 *
	 * @return ResponseEntity containing the list of all planters.
	 */
	@GetMapping(value = "")
	public ResponseEntity<List<Planter>> viewAllPlanters() {
		logger.info("Viewing all planters");
		return new ResponseEntity<>(planterService.viewAllPlanters(), HttpStatus.OK);
	}

	/**
	 * Fetches planters within a cost range.
	 *
	 * @param min The minimum cost of the planters to fetch.
	 * @param max The maximum cost of the planters to fetch.
	 * @return ResponseEntity containing the list of planters within the specified
	 *         cost range.
	 */
	@GetMapping(value = "/{min}/{max}")
	public ResponseEntity<List<Planter>> viewAllPlantInCostRange(@PathVariable Double min, @PathVariable Double max) {
		logger.info("Viewing planters in cost range: min={}, max={}", min, max);
		return new ResponseEntity<>(planterService.viewAllPlanters(min, max), HttpStatus.OK);
	}
}
