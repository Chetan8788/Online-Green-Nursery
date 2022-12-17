package com.masai.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Seed;

@Repository
public interface SeedDao extends JpaRepository<Seed, Integer> {
	
	public Seed findByCommonName(String commonName);
	public List<Seed> findByType(String type);

}
