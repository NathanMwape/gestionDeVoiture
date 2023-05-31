package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.Voiture;
import com.myapp.repository.VoitureRepository;
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
 * Integration tests for the {@link VoitureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoitureResourceIT {

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELE = "AAAAAAAAAA";
    private static final String UPDATED_MODELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBILITE = false;
    private static final Boolean UPDATED_DISPONIBILITE = true;

    private static final Double DEFAULT_TARIF_JOURNALIER = 1D;
    private static final Double UPDATED_TARIF_JOURNALIER = 2D;

    private static final String ENTITY_API_URL = "/api/voitures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoitureMockMvc;

    private Voiture voiture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voiture createEntity(EntityManager em) {
        Voiture voiture = new Voiture()
            .marque(DEFAULT_MARQUE)
            .modele(DEFAULT_MODELE)
            .description(DEFAULT_DESCRIPTION)
            .disponibilite(DEFAULT_DISPONIBILITE)
            .tarifJournalier(DEFAULT_TARIF_JOURNALIER);
        return voiture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voiture createUpdatedEntity(EntityManager em) {
        Voiture voiture = new Voiture()
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .description(UPDATED_DESCRIPTION)
            .disponibilite(UPDATED_DISPONIBILITE)
            .tarifJournalier(UPDATED_TARIF_JOURNALIER);
        return voiture;
    }

    @BeforeEach
    public void initTest() {
        voiture = createEntity(em);
    }

    @Test
    @Transactional
    void createVoiture() throws Exception {
        int databaseSizeBeforeCreate = voitureRepository.findAll().size();
        // Create the Voiture
        restVoitureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voiture)))
            .andExpect(status().isCreated());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeCreate + 1);
        Voiture testVoiture = voitureList.get(voitureList.size() - 1);
        assertThat(testVoiture.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testVoiture.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testVoiture.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVoiture.getDisponibilite()).isEqualTo(DEFAULT_DISPONIBILITE);
        assertThat(testVoiture.getTarifJournalier()).isEqualTo(DEFAULT_TARIF_JOURNALIER);
    }

    @Test
    @Transactional
    void createVoitureWithExistingId() throws Exception {
        // Create the Voiture with an existing ID
        voiture.setId(1L);

        int databaseSizeBeforeCreate = voitureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoitureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voiture)))
            .andExpect(status().isBadRequest());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoitures() throws Exception {
        // Initialize the database
        voitureRepository.saveAndFlush(voiture);

        // Get all the voitureList
        restVoitureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voiture.getId().intValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].modele").value(hasItem(DEFAULT_MODELE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE.booleanValue())))
            .andExpect(jsonPath("$.[*].tarifJournalier").value(hasItem(DEFAULT_TARIF_JOURNALIER.doubleValue())));
    }

    @Test
    @Transactional
    void getVoiture() throws Exception {
        // Initialize the database
        voitureRepository.saveAndFlush(voiture);

        // Get the voiture
        restVoitureMockMvc
            .perform(get(ENTITY_API_URL_ID, voiture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voiture.getId().intValue()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.modele").value(DEFAULT_MODELE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.disponibilite").value(DEFAULT_DISPONIBILITE.booleanValue()))
            .andExpect(jsonPath("$.tarifJournalier").value(DEFAULT_TARIF_JOURNALIER.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingVoiture() throws Exception {
        // Get the voiture
        restVoitureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoiture() throws Exception {
        // Initialize the database
        voitureRepository.saveAndFlush(voiture);

        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();

        // Update the voiture
        Voiture updatedVoiture = voitureRepository.findById(voiture.getId()).get();
        // Disconnect from session so that the updates on updatedVoiture are not directly saved in db
        em.detach(updatedVoiture);
        updatedVoiture
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .description(UPDATED_DESCRIPTION)
            .disponibilite(UPDATED_DISPONIBILITE)
            .tarifJournalier(UPDATED_TARIF_JOURNALIER);

        restVoitureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoiture.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVoiture))
            )
            .andExpect(status().isOk());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
        Voiture testVoiture = voitureList.get(voitureList.size() - 1);
        assertThat(testVoiture.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testVoiture.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testVoiture.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVoiture.getDisponibilite()).isEqualTo(UPDATED_DISPONIBILITE);
        assertThat(testVoiture.getTarifJournalier()).isEqualTo(UPDATED_TARIF_JOURNALIER);
    }

    @Test
    @Transactional
    void putNonExistingVoiture() throws Exception {
        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();
        voiture.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoitureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voiture.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voiture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoiture() throws Exception {
        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();
        voiture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoitureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voiture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoiture() throws Exception {
        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();
        voiture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoitureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voiture)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoitureWithPatch() throws Exception {
        // Initialize the database
        voitureRepository.saveAndFlush(voiture);

        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();

        // Update the voiture using partial update
        Voiture partialUpdatedVoiture = new Voiture();
        partialUpdatedVoiture.setId(voiture.getId());

        partialUpdatedVoiture.description(UPDATED_DESCRIPTION);

        restVoitureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoiture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoiture))
            )
            .andExpect(status().isOk());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
        Voiture testVoiture = voitureList.get(voitureList.size() - 1);
        assertThat(testVoiture.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testVoiture.getModele()).isEqualTo(DEFAULT_MODELE);
        assertThat(testVoiture.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVoiture.getDisponibilite()).isEqualTo(DEFAULT_DISPONIBILITE);
        assertThat(testVoiture.getTarifJournalier()).isEqualTo(DEFAULT_TARIF_JOURNALIER);
    }

    @Test
    @Transactional
    void fullUpdateVoitureWithPatch() throws Exception {
        // Initialize the database
        voitureRepository.saveAndFlush(voiture);

        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();

        // Update the voiture using partial update
        Voiture partialUpdatedVoiture = new Voiture();
        partialUpdatedVoiture.setId(voiture.getId());

        partialUpdatedVoiture
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .description(UPDATED_DESCRIPTION)
            .disponibilite(UPDATED_DISPONIBILITE)
            .tarifJournalier(UPDATED_TARIF_JOURNALIER);

        restVoitureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoiture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoiture))
            )
            .andExpect(status().isOk());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
        Voiture testVoiture = voitureList.get(voitureList.size() - 1);
        assertThat(testVoiture.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testVoiture.getModele()).isEqualTo(UPDATED_MODELE);
        assertThat(testVoiture.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVoiture.getDisponibilite()).isEqualTo(UPDATED_DISPONIBILITE);
        assertThat(testVoiture.getTarifJournalier()).isEqualTo(UPDATED_TARIF_JOURNALIER);
    }

    @Test
    @Transactional
    void patchNonExistingVoiture() throws Exception {
        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();
        voiture.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoitureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voiture.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voiture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoiture() throws Exception {
        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();
        voiture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoitureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voiture))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoiture() throws Exception {
        int databaseSizeBeforeUpdate = voitureRepository.findAll().size();
        voiture.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoitureMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(voiture)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voiture in the database
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoiture() throws Exception {
        // Initialize the database
        voitureRepository.saveAndFlush(voiture);

        int databaseSizeBeforeDelete = voitureRepository.findAll().size();

        // Delete the voiture
        restVoitureMockMvc
            .perform(delete(ENTITY_API_URL_ID, voiture.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voiture> voitureList = voitureRepository.findAll();
        assertThat(voitureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
