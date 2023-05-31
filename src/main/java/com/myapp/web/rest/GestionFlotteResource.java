package com.myapp.web.rest;

import com.myapp.domain.GestionFlotte;
import com.myapp.repository.GestionFlotteRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.GestionFlotte}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GestionFlotteResource {

    private final Logger log = LoggerFactory.getLogger(GestionFlotteResource.class);

    private static final String ENTITY_NAME = "gestionFlotte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestionFlotteRepository gestionFlotteRepository;

    public GestionFlotteResource(GestionFlotteRepository gestionFlotteRepository) {
        this.gestionFlotteRepository = gestionFlotteRepository;
    }

    /**
     * {@code POST  /gestion-flottes} : Create a new gestionFlotte.
     *
     * @param gestionFlotte the gestionFlotte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestionFlotte, or with status {@code 400 (Bad Request)} if the gestionFlotte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gestion-flottes")
    public ResponseEntity<GestionFlotte> createGestionFlotte(@RequestBody GestionFlotte gestionFlotte) throws URISyntaxException {
        log.debug("REST request to save GestionFlotte : {}", gestionFlotte);
        if (gestionFlotte.getId() != null) {
            throw new BadRequestAlertException("A new gestionFlotte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GestionFlotte result = gestionFlotteRepository.save(gestionFlotte);
        return ResponseEntity
            .created(new URI("/api/gestion-flottes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gestion-flottes/:id} : Updates an existing gestionFlotte.
     *
     * @param id the id of the gestionFlotte to save.
     * @param gestionFlotte the gestionFlotte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionFlotte,
     * or with status {@code 400 (Bad Request)} if the gestionFlotte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestionFlotte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gestion-flottes/{id}")
    public ResponseEntity<GestionFlotte> updateGestionFlotte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GestionFlotte gestionFlotte
    ) throws URISyntaxException {
        log.debug("REST request to update GestionFlotte : {}, {}", id, gestionFlotte);
        if (gestionFlotte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestionFlotte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestionFlotteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // no save call needed as we have no fields that can be updated
        GestionFlotte result = gestionFlotte;
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionFlotte.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gestion-flottes/:id} : Partial updates given fields of an existing gestionFlotte, field will ignore if it is null
     *
     * @param id the id of the gestionFlotte to save.
     * @param gestionFlotte the gestionFlotte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionFlotte,
     * or with status {@code 400 (Bad Request)} if the gestionFlotte is not valid,
     * or with status {@code 404 (Not Found)} if the gestionFlotte is not found,
     * or with status {@code 500 (Internal Server Error)} if the gestionFlotte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gestion-flottes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GestionFlotte> partialUpdateGestionFlotte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GestionFlotte gestionFlotte
    ) throws URISyntaxException {
        log.debug("REST request to partial update GestionFlotte partially : {}, {}", id, gestionFlotte);
        if (gestionFlotte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestionFlotte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestionFlotteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GestionFlotte> result = gestionFlotteRepository
            .findById(gestionFlotte.getId())
            .map(existingGestionFlotte -> {
                return existingGestionFlotte;
            })// .map(gestionFlotteRepository::save)
        ;

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionFlotte.getId().toString())
        );
    }

    /**
     * {@code GET  /gestion-flottes} : get all the gestionFlottes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestionFlottes in body.
     */
    @GetMapping("/gestion-flottes")
    public List<GestionFlotte> getAllGestionFlottes() {
        log.debug("REST request to get all GestionFlottes");
        return gestionFlotteRepository.findAll();
    }

    /**
     * {@code GET  /gestion-flottes/:id} : get the "id" gestionFlotte.
     *
     * @param id the id of the gestionFlotte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestionFlotte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gestion-flottes/{id}")
    public ResponseEntity<GestionFlotte> getGestionFlotte(@PathVariable Long id) {
        log.debug("REST request to get GestionFlotte : {}", id);
        Optional<GestionFlotte> gestionFlotte = gestionFlotteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gestionFlotte);
    }

    /**
     * {@code DELETE  /gestion-flottes/:id} : delete the "id" gestionFlotte.
     *
     * @param id the id of the gestionFlotte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gestion-flottes/{id}")
    public ResponseEntity<Void> deleteGestionFlotte(@PathVariable Long id) {
        log.debug("REST request to delete GestionFlotte : {}", id);
        gestionFlotteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
