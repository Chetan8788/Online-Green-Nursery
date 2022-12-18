package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Planter;

@Repository
public interface PlanterDao extends JpaRepository<Planter, Integer> {

	List<Planter> findByShape(String plantrShape);

	List<Planter> findByCostBetweenOrderByCost(Double minCost, Double maxCost);

}
