package com.dwithrow.evilbook.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dwithrow.evilbook.models.Bio;

@Repository
public interface BioRepository extends CrudRepository<Bio, Long> {
	Optional<Bio> findById(Long id);
	@Query("Select b from Bio b where user_id = ?1")
	Bio findByUserId(Long user_id);
}
