package com.gksoft.maintenance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gksoft.maintenance.IntegrationTest;
import com.gksoft.maintenance.domain.Actions;
import com.gksoft.maintenance.repository.ActionsRepository;
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
 * Integration tests for the {@link ActionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActionsResourceIT {

    private static final String DEFAULT_ACTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionsMockMvc;

    private Actions actions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actions createEntity(EntityManager em) {
        Actions actions = new Actions().actionName(DEFAULT_ACTION_NAME);
        return actions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Actions createUpdatedEntity(EntityManager em) {
        Actions actions = new Actions().actionName(UPDATED_ACTION_NAME);
        return actions;
    }

    @BeforeEach
    public void initTest() {
        actions = createEntity(em);
    }

    @Test
    @Transactional
    void createActions() throws Exception {
        int databaseSizeBeforeCreate = actionsRepository.findAll().size();
        // Create the Actions
        restActionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actions)))
            .andExpect(status().isCreated());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeCreate + 1);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getActionName()).isEqualTo(DEFAULT_ACTION_NAME);
    }

    @Test
    @Transactional
    void createActionsWithExistingId() throws Exception {
        // Create the Actions with an existing ID
        actions.setId(1L);

        int databaseSizeBeforeCreate = actionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actions)))
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        // Get all the actionsList
        restActionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actions.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionName").value(hasItem(DEFAULT_ACTION_NAME)));
    }

    @Test
    @Transactional
    void getActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        // Get the actions
        restActionsMockMvc
            .perform(get(ENTITY_API_URL_ID, actions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actions.getId().intValue()))
            .andExpect(jsonPath("$.actionName").value(DEFAULT_ACTION_NAME));
    }

    @Test
    @Transactional
    void getNonExistingActions() throws Exception {
        // Get the actions
        restActionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();

        // Update the actions
        Actions updatedActions = actionsRepository.findById(actions.getId()).get();
        // Disconnect from session so that the updates on updatedActions are not directly saved in db
        em.detach(updatedActions);
        updatedActions.actionName(UPDATED_ACTION_NAME);

        restActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActions))
            )
            .andExpect(status().isOk());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getActionName()).isEqualTo(UPDATED_ACTION_NAME);
    }

    @Test
    @Transactional
    void putNonExistingActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActionsWithPatch() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();

        // Update the actions using partial update
        Actions partialUpdatedActions = new Actions();
        partialUpdatedActions.setId(actions.getId());

        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActions))
            )
            .andExpect(status().isOk());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getActionName()).isEqualTo(DEFAULT_ACTION_NAME);
    }

    @Test
    @Transactional
    void fullUpdateActionsWithPatch() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();

        // Update the actions using partial update
        Actions partialUpdatedActions = new Actions();
        partialUpdatedActions.setId(actions.getId());

        partialUpdatedActions.actionName(UPDATED_ACTION_NAME);

        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActions))
            )
            .andExpect(status().isOk());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
        Actions testActions = actionsList.get(actionsList.size() - 1);
        assertThat(testActions.getActionName()).isEqualTo(UPDATED_ACTION_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, actions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActions() throws Exception {
        int databaseSizeBeforeUpdate = actionsRepository.findAll().size();
        actions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(actions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Actions in the database
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActions() throws Exception {
        // Initialize the database
        actionsRepository.saveAndFlush(actions);

        int databaseSizeBeforeDelete = actionsRepository.findAll().size();

        // Delete the actions
        restActionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, actions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Actions> actionsList = actionsRepository.findAll();
        assertThat(actionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
