package com.dwithrow.evilbook.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dwithrow.evilbook.models.Bio;
import com.dwithrow.evilbook.repositories.BioRepository;

@Service
public class BioService {
	private final BioRepository bioRepository;
	
	public BioService(BioRepository bioRepository) {
		this.bioRepository = bioRepository;
	}
	
	public Bio createBio(Bio bio) {
		return bioRepository.save(bio);
	}
	
	public Bio findBioById(Long bio_id) {
		Optional<Bio> bio =bioRepository.findById(bio_id);
		if (bio.isPresent()) {
			return bio.get();
		}
		return null;
	}

	public Bio findBioByUserId(Long user_id) {
		return bioRepository.findByUserId(user_id);
	}

	public Bio updateBio(Bio bio) {
		return bioRepository.save(bio);
	}
	
}
