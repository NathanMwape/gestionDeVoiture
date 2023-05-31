package com.myapp.repository;

import com.myapp.domain.Voiture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voiture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {}
