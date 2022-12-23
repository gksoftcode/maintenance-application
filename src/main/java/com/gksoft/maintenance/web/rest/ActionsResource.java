package com.gksoft.maintenance.web.rest;

import com.gksoft.maintenance.domain.Actions;
import com.gksoft.maintenance.repository.ActionsRepository;
import com.gksoft.maintenance.service.ActionsService;
import com.gksoft.maintenance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gksoft.maintenance.domain.Actions}.
 */
@RestController
@RequestMapping("/api")
public class ActionsResource {

    private final Logger log = LoggerFactory.getLogger(ActionsResource.class);

    private static final String ENTITY_NAME = "actions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionsService actionsService;

    private final ActionsRepository actionsRepository;

    public ActionsResource(ActionsService actionsService, ActionsRepository actionsRepository) {
        this.actionsService = actionsService;
        this.actionsRepository = actionsRepository;
    }

    /**
     * {@code POST  /actions} : Create a new actions.
     *
     * @param actions the actions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actions, or with status {@code 400 (Bad Request)} if the actions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actions")
    public ResponseEntity<Actions> createActions(@RequestBody Actions actions) throws URISyntaxException {
        log.debug("REST request to save Actions : {}", actions);
        if (actions.getId() != null) {
            throw new BadRequestAlertException("A new actions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Actions result = actionsService.save(actions);
        return ResponseEntity
            .created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actions/:id} : Updates an existing actions.
     *
     * @param id the id of the actions to save.
     * @param actions the actions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actions,
     * or with status {@code 400 (Bad Request)} if the actions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actions/{id}")
    public ResponseEntity<Actions> updateActions(@PathVariable(value = "id", required = false) final Long id, @RequestBody Actions actions)
        throws URISyntaxException {
        log.debug("REST request to update Actions : {}, {}", id, actions);
        if (actions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Actions result = actionsService.update(actions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /actions/:id} : Partial updates given fields of an existing actions, field will ignore if it is null
     *
     * @param id the id of the actions to save.
     * @param actions the actions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actions,
     * or with status {@code 400 (Bad Request)} if the actions is not valid,
     * or with status {@code 404 (Not Found)} if the actions is not found,
     * or with status {@code 500 (Internal Server Error)} if the actions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/actions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Actions> partialUpdateActions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Actions actions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Actions partially : {}, {}", id, actions);
        if (actions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Actions> result = actionsService.partialUpdate(actions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actions.getId().toString())
        );
    }

    /**
     * {@code GET  /actions} : get all the actions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actions in body.
     */
    @GetMapping("/actions")
    public List<Actions> getAllActions() {
        log.debug("REST request to get all Actions");
        return actionsService.findAll();
    }

    /**
     * {@code GET  /actions/:id} : get the "id" actions.
     *
     * @param id the id of the actions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actions/{id}")
    public ResponseEntity<Actions> getActions(@PathVariable Long id) {
        log.debug("REST request to get Actions : {}", id);
        Optional<Actions> actions = actionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actions);
    }

    /**
     * {@code DELETE  /actions/:id} : delete the "id" actions.
     *
     * @param id the id of the actions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actions/{id}")
    public ResponseEntity<Void> deleteActions(@PathVariable Long id) {
        log.debug("REST request to delete Actions : {}", id);
        actionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
