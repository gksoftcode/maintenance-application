package com.gksoft.maintenance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gksoft.maintenance.IntegrationTest;
import com.gksoft.maintenance.domain.WorkOrdersHistory;
import com.gksoft.maintenance.repository.WorkOrdersHistoryRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link WorkOrdersHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkOrdersHistoryResourceIT {

    private static final Boolean DEFAULT_IS_SHARED = false;
    private static final Boolean UPDATED_IS_SHARED = true;

    private static final Instant DEFAULT_ACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-orders-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkOrdersHistoryRepository workOrdersHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkOrdersHistoryMockMvc;

    private WorkOrdersHistory workOrdersHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkOrdersHistory createEntity(EntityManager em) {
        WorkOrdersHistory workOrdersHistory = new WorkOrdersHistory()
            .isShared(DEFAULT_IS_SHARED)
            .actionDate(DEFAULT_ACTION_DATE)
            .note(DEFAULT_NOTE);
        return workOrdersHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkOrdersHistory createUpdatedEntity(EntityManager em) {
        WorkOrdersHistory workOrdersHistory = new WorkOrdersHistory()
            .isShared(UPDATED_IS_SHARED)
            .actionDate(UPDATED_ACTION_DATE)
            .note(UPDATED_NOTE);
        return workOrdersHistory;
    }

    @BeforeEach
    public void initTest() {
        workOrdersHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeCreate = workOrdersHistoryRepository.findAll().size();
        // Create the WorkOrdersHistory
        restWorkOrdersHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isCreated());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkOrdersHistory testWorkOrdersHistory = workOrdersHistoryList.get(workOrdersHistoryList.size() - 1);
        assertThat(testWorkOrdersHistory.getIsShared()).isEqualTo(DEFAULT_IS_SHARED);
        assertThat(testWorkOrdersHistory.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testWorkOrdersHistory.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createWorkOrdersHistoryWithExistingId() throws Exception {
        // Create the WorkOrdersHistory with an existing ID
        workOrdersHistory.setId(1L);

        int databaseSizeBeforeCreate = workOrdersHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkOrdersHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkOrdersHistories() throws Exception {
        // Initialize the database
        workOrdersHistoryRepository.saveAndFlush(workOrdersHistory);

        // Get all the workOrdersHistoryList
        restWorkOrdersHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrdersHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].isShared").value(hasItem(DEFAULT_IS_SHARED.booleanValue())))
            .andExpect(jsonPath("$.[*].actionDate").value(hasItem(DEFAULT_ACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getWorkOrdersHistory() throws Exception {
        // Initialize the database
        workOrdersHistoryRepository.saveAndFlush(workOrdersHistory);

        // Get the workOrdersHistory
        restWorkOrdersHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, workOrdersHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workOrdersHistory.getId().intValue()))
            .andExpect(jsonPath("$.isShared").value(DEFAULT_IS_SHARED.booleanValue()))
            .andExpect(jsonPath("$.actionDate").value(DEFAULT_ACTION_DATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingWorkOrdersHistory() throws Exception {
        // Get the workOrdersHistory
        restWorkOrdersHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkOrdersHistory() throws Exception {
        // Initialize the database
        workOrdersHistoryRepository.saveAndFlush(workOrdersHistory);

        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();

        // Update the workOrdersHistory
        WorkOrdersHistory updatedWorkOrdersHistory = workOrdersHistoryRepository.findById(workOrdersHistory.getId()).get();
        // Disconnect from session so that the updates on updatedWorkOrdersHistory are not directly saved in db
        em.detach(updatedWorkOrdersHistory);
        updatedWorkOrdersHistory.isShared(UPDATED_IS_SHARED).actionDate(UPDATED_ACTION_DATE).note(UPDATED_NOTE);

        restWorkOrdersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkOrdersHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkOrdersHistory))
            )
            .andExpect(status().isOk());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
        WorkOrdersHistory testWorkOrdersHistory = workOrdersHistoryList.get(workOrdersHistoryList.size() - 1);
        assertThat(testWorkOrdersHistory.getIsShared()).isEqualTo(UPDATED_IS_SHARED);
        assertThat(testWorkOrdersHistory.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testWorkOrdersHistory.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();
        workOrdersHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkOrdersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workOrdersHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();
        workOrdersHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrdersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();
        workOrdersHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrdersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkOrdersHistoryWithPatch() throws Exception {
        // Initialize the database
        workOrdersHistoryRepository.saveAndFlush(workOrdersHistory);

        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();

        // Update the workOrdersHistory using partial update
        WorkOrdersHistory partialUpdatedWorkOrdersHistory = new WorkOrdersHistory();
        partialUpdatedWorkOrdersHistory.setId(workOrdersHistory.getId());

        partialUpdatedWorkOrdersHistory.isShared(UPDATED_IS_SHARED).actionDate(UPDATED_ACTION_DATE).note(UPDATED_NOTE);

        restWorkOrdersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkOrdersHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkOrdersHistory))
            )
            .andExpect(status().isOk());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
        WorkOrdersHistory testWorkOrdersHistory = workOrdersHistoryList.get(workOrdersHistoryList.size() - 1);
        assertThat(testWorkOrdersHistory.getIsShared()).isEqualTo(UPDATED_IS_SHARED);
        assertThat(testWorkOrdersHistory.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testWorkOrdersHistory.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateWorkOrdersHistoryWithPatch() throws Exception {
        // Initialize the database
        workOrdersHistoryRepository.saveAndFlush(workOrdersHistory);

        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();

        // Update the workOrdersHistory using partial update
        WorkOrdersHistory partialUpdatedWorkOrdersHistory = new WorkOrdersHistory();
        partialUpdatedWorkOrdersHistory.setId(workOrdersHistory.getId());

        partialUpdatedWorkOrdersHistory.isShared(UPDATED_IS_SHARED).actionDate(UPDATED_ACTION_DATE).note(UPDATED_NOTE);

        restWorkOrdersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkOrdersHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkOrdersHistory))
            )
            .andExpect(status().isOk());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
        WorkOrdersHistory testWorkOrdersHistory = workOrdersHistoryList.get(workOrdersHistoryList.size() - 1);
        assertThat(testWorkOrdersHistory.getIsShared()).isEqualTo(UPDATED_IS_SHARED);
        assertThat(testWorkOrdersHistory.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testWorkOrdersHistory.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();
        workOrdersHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkOrdersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workOrdersHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();
        workOrdersHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrdersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkOrdersHistory() throws Exception {
        int databaseSizeBeforeUpdate = workOrdersHistoryRepository.findAll().size();
        workOrdersHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkOrdersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workOrdersHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkOrdersHistory in the database
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkOrdersHistory() throws Exception {
        // Initialize the database
        workOrdersHistoryRepository.saveAndFlush(workOrdersHistory);

        int databaseSizeBeforeDelete = workOrdersHistoryRepository.findAll().size();

        // Delete the workOrdersHistory
        restWorkOrdersHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, workOrdersHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkOrdersHistory> workOrdersHistoryList = workOrdersHistoryRepository.findAll();
        assertThat(workOrdersHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
