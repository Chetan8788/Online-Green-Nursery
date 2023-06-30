package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "planters")
public class PlanterController {
	@Autowired
	PlanterService planterService;

	@PostMapping(value = "")
	public ResponseEntity<Planter> addPlanter(@Valid @RequestBody Planter planter) {
		return new ResponseEntity<Planter>(planterService.addPlanter(planter), HttpStatus.CREATED);

	}

	@PatchMapping(value = "seeds")
	public ResponseEntity<String> addSeedsInPlanter(@RequestParam Integer planterId, @RequestParam Integer SeedId) {
		return new ResponseEntity<String>(planterService.addSeedsInPlanter(planterId, SeedId), HttpStatus.CREATED);

	}

	@PatchMapping(value = "plants")
	public ResponseEntity<String> addPlanInPlanter(@RequestParam Integer planterId, @RequestParam Integer plantId) {
		return new ResponseEntity<String>(planterService.addPlantInPlanter(planterId, plantId), HttpStatus.CREATED);

	}

	@PutMapping(value = "")
	public ResponseEntity<Planter> updatePlanter(@Valid @RequestBody Planter planter) {

		return new ResponseEntity<Planter>(planterService.updatePlanter(planter), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Planter> deletePlant(@PathVariable Integer id) {
		return new ResponseEntity<Planter>(planterService.deletePlanter(id), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Planter> viewPlanter(@PathVariable Integer id) {
		return new ResponseEntity<Planter>(planterService.viewPlanter(id), HttpStatus.OK);
	}

	@GetMapping(value = "byShape/{plantrShape}")
	public ResponseEntity<List<Planter>> viewPlantByShape(@PathVariable String plantrShape) {
		return new ResponseEntity<List<Planter>>(planterService.viewPlanter(plantrShape), HttpStatus.OK);
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Planter>> viewAllPlanters() {
		return new ResponseEntity<List<Planter>>(planterService.viewAllPlanters(), HttpStatus.OK);
	}

	@GetMapping(value = "/{min}/{max}")
	public ResponseEntity<List<Planter>> viewAllPlantInCostRange(@PathVariable Double min, @PathVariable Double max) {
		return new ResponseEntity<List<Planter>>(planterService.viewAllPlanters(min, max), HttpStatus.OK);
	}

}
