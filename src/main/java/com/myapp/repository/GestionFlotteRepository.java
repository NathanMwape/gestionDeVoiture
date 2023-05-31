package com.myapp.repository;

import com.myapp.domain.GestionFlotte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GestionFlotte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GestionFlotteRepository extends JpaRepository<GestionFlotte, Long> {}
