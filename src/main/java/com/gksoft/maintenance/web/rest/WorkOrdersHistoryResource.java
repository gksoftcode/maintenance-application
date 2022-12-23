package com.gksoft.maintenance.web.rest;

import com.gksoft.maintenance.domain.WorkOrdersHistory;
import com.gksoft.maintenance.repository.WorkOrdersHistoryRepository;
import com.gksoft.maintenance.service.WorkOrdersHistoryService;
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
 * REST controller for managing {@link com.gksoft.maintenance.domain.WorkOrdersHistory}.
 */
@RestController
@RequestMapping("/api")
public class WorkOrdersHistoryResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrdersHistoryResource.class);

    private static final String ENTITY_NAME = "workOrdersHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkOrdersHistoryService workOrdersHistoryService;

    private final WorkOrdersHistoryRepository workOrdersHistoryRepository;

    public WorkOrdersHistoryResource(
        WorkOrdersHistoryService workOrdersHistoryService,
        WorkOrdersHistoryRepository workOrdersHistoryRepository
    ) {
        this.workOrdersHistoryService = workOrdersHistoryService;
        this.workOrdersHistoryRepository = workOrdersHistoryRepository;
    }

    /**
     * {@code POST  /work-orders-histories} : Create a new workOrdersHistory.
     *
     * @param workOrdersHistory the workOrdersHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workOrdersHistory, or with status {@code 400 (Bad Request)} if the workOrdersHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-orders-histories")
    public ResponseEntity<WorkOrdersHistory> createWorkOrdersHistory(@RequestBody WorkOrdersHistory workOrdersHistory)
        throws URISyntaxException {
        log.debug("REST request to save WorkOrdersHistory : {}", workOrdersHistory);
        if (workOrdersHistory.getId() != null) {
            throw new BadRequestAlertException("A new workOrdersHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkOrdersHistory result = workOrdersHistoryService.save(workOrdersHistory);
        return ResponseEntity
            .created(new URI("/api/work-orders-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-orders-histories/:id} : Updates an existing workOrdersHistory.
     *
     * @param id the id of the workOrdersHistory to save.
     * @param workOrdersHistory the workOrdersHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workOrdersHistory,
     * or with status {@code 400 (Bad Request)} if the workOrdersHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workOrdersHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-orders-histories/{id}")
    public ResponseEntity<WorkOrdersHistory> updateWorkOrdersHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkOrdersHistory workOrdersHistory
    ) throws URISyntaxException {
        log.debug("REST request to update WorkOrdersHistory : {}, {}", id, workOrdersHistory);
        if (workOrdersHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workOrdersHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workOrdersHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkOrdersHistory result = workOrdersHistoryService.update(workOrdersHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workOrdersHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-orders-histories/:id} : Partial updates given fields of an existing workOrdersHistory, field will ignore if it is null
     *
     * @param id the id of the workOrdersHistory to save.
     * @param workOrdersHistory the workOrdersHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workOrdersHistory,
     * or with status {@code 400 (Bad Request)} if the workOrdersHistory is not valid,
     * or with status {@code 404 (Not Found)} if the workOrdersHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the workOrdersHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-orders-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkOrdersHistory> partialUpdateWorkOrdersHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkOrdersHistory workOrdersHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkOrdersHistory partially : {}, {}", id, workOrdersHistory);
        if (workOrdersHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workOrdersHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workOrdersHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkOrdersHistory> result = workOrdersHistoryService.partialUpdate(workOrdersHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workOrdersHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /work-orders-histories} : get all the workOrdersHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workOrdersHistories in body.
     */
    @GetMapping("/work-orders-histories")
    public List<WorkOrdersHistory> getAllWorkOrdersHistories() {
        log.debug("REST request to get all WorkOrdersHistories");
        return workOrdersHistoryService.findAll();
    }

    /**
     * {@code GET  /work-orders-histories/:id} : get the "id" workOrdersHistory.
     *
     * @param id the id of the workOrdersHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workOrdersHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-orders-histories/{id}")
    public ResponseEntity<WorkOrdersHistory> getWorkOrdersHistory(@PathVariable Long id) {
        log.debug("REST request to get WorkOrdersHistory : {}", id);
        Optional<WorkOrdersHistory> workOrdersHistory = workOrdersHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workOrdersHistory);
    }

    /**
     * {@code DELETE  /work-orders-histories/:id} : delete the "id" workOrdersHistory.
     *
     * @param id the id of the workOrdersHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-orders-histories/{id}")
    public ResponseEntity<Void> deleteWorkOrdersHistory(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrdersHistory : {}", id);
        workOrdersHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
