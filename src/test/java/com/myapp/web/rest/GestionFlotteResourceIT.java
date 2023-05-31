package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.GestionFlotte;
import com.myapp.repository.GestionFlotteRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GestionFlotteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GestionFlotteResourceIT {

    private static final String ENTITY_API_URL = "/api/gestion-flottes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GestionFlotteRepository gestionFlotteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestionFlotteMockMvc;

    private GestionFlotte gestionFlotte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestionFlotte createEntity(EntityManager em) {
        GestionFlotte gestionFlotte = new GestionFlotte();
        return gestionFlotte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GestionFlotte createUpdatedEntity(EntityManager em) {
        GestionFlotte gestionFlotte = new GestionFlotte();
        return gestionFlotte;
    }

    @BeforeEach
    public void initTest() {
        gestionFlotte = createEntity(em);
    }

    @Test
    @Transactional
    void createGestionFlotte() throws Exception {
        int databaseSizeBeforeCreate = gestionFlotteRepository.findAll().size();
        // Create the GestionFlotte
        restGestionFlotteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionFlotte)))
            .andExpect(status().isCreated());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeCreate + 1);
        GestionFlotte testGestionFlotte = gestionFlotteList.get(gestionFlotteList.size() - 1);
    }

    @Test
    @Transactional
    void createGestionFlotteWithExistingId() throws Exception {
        // Create the GestionFlotte with an existing ID
        gestionFlotte.setId(1L);

        int databaseSizeBeforeCreate = gestionFlotteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestionFlotteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionFlotte)))
            .andExpect(status().isBadRequest());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGestionFlottes() throws Exception {
        // Initialize the database
        gestionFlotteRepository.saveAndFlush(gestionFlotte);

        // Get all the gestionFlotteList
        restGestionFlotteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionFlotte.getId().intValue())));
    }

    @Test
    @Transactional
    void getGestionFlotte() throws Exception {
        // Initialize the database
        gestionFlotteRepository.saveAndFlush(gestionFlotte);

        // Get the gestionFlotte
        restGestionFlotteMockMvc
            .perform(get(ENTITY_API_URL_ID, gestionFlotte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestionFlotte.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGestionFlotte() throws Exception {
        // Get the gestionFlotte
        restGestionFlotteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGestionFlotte() throws Exception {
        // Initialize the database
        gestionFlotteRepository.saveAndFlush(gestionFlotte);

        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();

        // Update the gestionFlotte
        GestionFlotte updatedGestionFlotte = gestionFlotteRepository.findById(gestionFlotte.getId()).get();
        // Disconnect from session so that the updates on updatedGestionFlotte are not directly saved in db
        em.detach(updatedGestionFlotte);

        restGestionFlotteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGestionFlotte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGestionFlotte))
            )
            .andExpect(status().isOk());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
        GestionFlotte testGestionFlotte = gestionFlotteList.get(gestionFlotteList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingGestionFlotte() throws Exception {
        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();
        gestionFlotte.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionFlotteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestionFlotte.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gestionFlotte))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGestionFlotte() throws Exception {
        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();
        gestionFlotte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionFlotteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gestionFlotte))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGestionFlotte() throws Exception {
        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();
        gestionFlotte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionFlotteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionFlotte)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGestionFlotteWithPatch() throws Exception {
        // Initialize the database
        gestionFlotteRepository.saveAndFlush(gestionFlotte);

        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();

        // Update the gestionFlotte using partial update
        GestionFlotte partialUpdatedGestionFlotte = new GestionFlotte();
        partialUpdatedGestionFlotte.setId(gestionFlotte.getId());

        restGestionFlotteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestionFlotte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGestionFlotte))
            )
            .andExpect(status().isOk());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
        GestionFlotte testGestionFlotte = gestionFlotteList.get(gestionFlotteList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateGestionFlotteWithPatch() throws Exception {
        // Initialize the database
        gestionFlotteRepository.saveAndFlush(gestionFlotte);

        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();

        // Update the gestionFlotte using partial update
        GestionFlotte partialUpdatedGestionFlotte = new GestionFlotte();
        partialUpdatedGestionFlotte.setId(gestionFlotte.getId());

        restGestionFlotteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestionFlotte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGestionFlotte))
            )
            .andExpect(status().isOk());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
        GestionFlotte testGestionFlotte = gestionFlotteList.get(gestionFlotteList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingGestionFlotte() throws Exception {
        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();
        gestionFlotte.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionFlotteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gestionFlotte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gestionFlotte))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGestionFlotte() throws Exception {
        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();
        gestionFlotte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionFlotteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gestionFlotte))
            )
            .andExpect(status().isBadRequest());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGestionFlotte() throws Exception {
        int databaseSizeBeforeUpdate = gestionFlotteRepository.findAll().size();
        gestionFlotte.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionFlotteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gestionFlotte))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GestionFlotte in the database
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGestionFlotte() throws Exception {
        // Initialize the database
        gestionFlotteRepository.saveAndFlush(gestionFlotte);

        int databaseSizeBeforeDelete = gestionFlotteRepository.findAll().size();

        // Delete the gestionFlotte
        restGestionFlotteMockMvc
            .perform(delete(ENTITY_API_URL_ID, gestionFlotte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GestionFlotte> gestionFlotteList = gestionFlotteRepository.findAll();
        assertThat(gestionFlotteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
