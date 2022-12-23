package com.gksoft.maintenance.service;

import com.gksoft.maintenance.domain.Status;
import com.gksoft.maintenance.repository.StatusRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Status}.
 */
@Service
@Transactional
public class StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusService.class);

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Save a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        return statusRepository.save(status);
    }

    /**
     * Update a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
    public Status update(Status status) {
        log.debug("Request to update Status : {}", status);
        return statusRepository.save(status);
    }

    /**
     * Partially update a status.
     *
     * @param status the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Status> partialUpdate(Status status) {
        log.debug("Request to partially update Status : {}", status);

        return statusRepository
            .findById(status.getId())
            .map(existingStatus -> {
                if (status.getStatusName() != null) {
                    existingStatus.setStatusName(status.getStatusName());
                }

                return existingStatus;
            })
            .map(statusRepository::save);
    }

    /**
     * Get all the statuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Status> findAll() {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll();
    }

    /**
     * Get one status by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Status> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id);
    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }
}
