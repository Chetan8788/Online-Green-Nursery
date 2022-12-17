package com.masai.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.SeedException;
import com.masai.model.Planter;
import com.masai.model.Seed;
import com.masai.repository.SeedDao;

@Service
public class SeedServiceImpl implements SeedService{
	
	@Autowired
	private SeedDao seedDao;

	@Override
	public Seed addSeed(Seed seed)throws SeedException {
		
		Set<Planter> planters= seed.getPlanters();
		for(Planter planter:planters) {
			planter.getSeeds().add(seed);
		}
		
		Seed registeredSeed=seedDao.save(seed);
		if(seed !=null) return registeredSeed;
		
		else throw new SeedException("Seed couldn't add");
	
	}

	@Override
	public Seed updateSeed(Seed seed) throws SeedException {
		Optional<Seed> optional= seedDao.findById(seed.getSeedId());
		if(optional.isPresent()) {
			Seed existingSeed=optional.get();
			seedDao.save(seed);
			return existingSeed;
		}else throw new SeedException("Seed not found with seedId : "+seed.getSeedId());
		
	}

	@Override
	public Seed deleteSeed(Integer seedId) throws SeedException {
		
		Optional<Seed> optional= seedDao.findById(seedId);
		
		if(optional.isPresent()) {
			seedDao.deleteById(seedId);
			return optional.get();
		}else throw new SeedException("Seed not found with seedId : "+seedId);
			
	}

	@Override
	public Seed viewSeed(Integer seedId) throws SeedException {
		
        Optional<Seed> optional= seedDao.findById(seedId);
		
		if(optional.isPresent()) {
			
			return optional.get();
			
		}else throw new SeedException("Seed not found with seedId : "+seedId);
			
	}

	@Override
	public Seed viewSeedByCommonName(String commonName) throws SeedException {
		Seed seed=seedDao.findByCommonName(commonName);
		if(seed!=null) {
			return seed;
		}else throw new SeedException("Seed not found with common name : "+commonName);
		
	}

	@Override
	public List<Seed> viewAllSeeds() throws SeedException {
		
		List<Seed> seeds=seedDao.findAll();
		if(seeds!=null) {
			return seeds;
		} throw new SeedException("Seeds not found");
		
		
	}

	@Override
	public List<Seed> viewAllSeedsByType(String typeOfSeed) throws SeedException {
		
		List<Seed> seeds=seedDao.findByType(typeOfSeed);
		if(seeds!=null) {
			return seeds;
		} throw new SeedException("Seeds not found");
		
	}

}
