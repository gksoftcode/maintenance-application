package com.gksoft.maintenance.service;

import com.gksoft.maintenance.domain.WorkOrdersHistory;
import com.gksoft.maintenance.repository.WorkOrdersHistoryRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkOrdersHistory}.
 */
@Service
@Transactional
public class WorkOrdersHistoryService {

    private final Logger log = LoggerFactory.getLogger(WorkOrdersHistoryService.class);

    private final WorkOrdersHistoryRepository workOrdersHistoryRepository;

    public WorkOrdersHistoryService(WorkOrdersHistoryRepository workOrdersHistoryRepository) {
        this.workOrdersHistoryRepository = workOrdersHistoryRepository;
    }

    /**
     * Save a workOrdersHistory.
     *
     * @param workOrdersHistory the entity to save.
     * @return the persisted entity.
     */
    public WorkOrdersHistory save(WorkOrdersHistory workOrdersHistory) {
        log.debug("Request to save WorkOrdersHistory : {}", workOrdersHistory);
        return workOrdersHistoryRepository.save(workOrdersHistory);
    }

    /**
     * Update a workOrdersHistory.
     *
     * @param workOrdersHistory the entity to save.
     * @return the persisted entity.
     */
    public WorkOrdersHistory update(WorkOrdersHistory workOrdersHistory) {
        log.debug("Request to update WorkOrdersHistory : {}", workOrdersHistory);
        return workOrdersHistoryRepository.save(workOrdersHistory);
    }

    /**
     * Partially update a workOrdersHistory.
     *
     * @param workOrdersHistory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkOrdersHistory> partialUpdate(WorkOrdersHistory workOrdersHistory) {
        log.debug("Request to partially update WorkOrdersHistory : {}", workOrdersHistory);

        return workOrdersHistoryRepository
            .findById(workOrdersHistory.getId())
            .map(existingWorkOrdersHistory -> {
                if (workOrdersHistory.getIsShared() != null) {
                    existingWorkOrdersHistory.setIsShared(workOrdersHistory.getIsShared());
                }
                if (workOrdersHistory.getActionDate() != null) {
                    existingWorkOrdersHistory.setActionDate(workOrdersHistory.getActionDate());
                }
                if (workOrdersHistory.getNote() != null) {
                    existingWorkOrdersHistory.setNote(workOrdersHistory.getNote());
                }

                return existingWorkOrdersHistory;
            })
            .map(workOrdersHistoryRepository::save);
    }

    /**
     * Get all the workOrdersHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WorkOrdersHistory> findAll() {
        log.debug("Request to get all WorkOrdersHistories");
        return workOrdersHistoryRepository.findAll();
    }

    /**
     * Get one workOrdersHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkOrdersHistory> findOne(Long id) {
        log.debug("Request to get WorkOrdersHistory : {}", id);
        return workOrdersHistoryRepository.findById(id);
    }

    /**
     * Delete the workOrdersHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkOrdersHistory : {}", id);
        workOrdersHistoryRepository.deleteById(id);
    }
}
