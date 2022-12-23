package com.gksoft.maintenance.service;

import com.gksoft.maintenance.domain.Actions;
import com.gksoft.maintenance.repository.ActionsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Actions}.
 */
@Service
@Transactional
public class ActionsService {

    private final Logger log = LoggerFactory.getLogger(ActionsService.class);

    private final ActionsRepository actionsRepository;

    public ActionsService(ActionsRepository actionsRepository) {
        this.actionsRepository = actionsRepository;
    }

    /**
     * Save a actions.
     *
     * @param actions the entity to save.
     * @return the persisted entity.
     */
    public Actions save(Actions actions) {
        log.debug("Request to save Actions : {}", actions);
        return actionsRepository.save(actions);
    }

    /**
     * Update a actions.
     *
     * @param actions the entity to save.
     * @return the persisted entity.
     */
    public Actions update(Actions actions) {
        log.debug("Request to update Actions : {}", actions);
        return actionsRepository.save(actions);
    }

    /**
     * Partially update a actions.
     *
     * @param actions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Actions> partialUpdate(Actions actions) {
        log.debug("Request to partially update Actions : {}", actions);

        return actionsRepository
            .findById(actions.getId())
            .map(existingActions -> {
                if (actions.getActionName() != null) {
                    existingActions.setActionName(actions.getActionName());
                }

                return existingActions;
            })
            .map(actionsRepository::save);
    }

    /**
     * Get all the actions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Actions> findAll() {
        log.debug("Request to get all Actions");
        return actionsRepository.findAll();
    }

    /**
     * Get one actions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Actions> findOne(Long id) {
        log.debug("Request to get Actions : {}", id);
        return actionsRepository.findById(id);
    }

    /**
     * Delete the actions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Actions : {}", id);
        actionsRepository.deleteById(id);
    }
}
