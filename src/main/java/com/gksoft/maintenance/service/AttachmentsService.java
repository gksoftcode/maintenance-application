package com.gksoft.maintenance.service;

import com.gksoft.maintenance.domain.Attachments;
import com.gksoft.maintenance.repository.AttachmentsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attachments}.
 */
@Service
@Transactional
public class AttachmentsService {

    private final Logger log = LoggerFactory.getLogger(AttachmentsService.class);

    private final AttachmentsRepository attachmentsRepository;

    public AttachmentsService(AttachmentsRepository attachmentsRepository) {
        this.attachmentsRepository = attachmentsRepository;
    }

    /**
     * Save a attachments.
     *
     * @param attachments the entity to save.
     * @return the persisted entity.
     */
    public Attachments save(Attachments attachments) {
        log.debug("Request to save Attachments : {}", attachments);
        return attachmentsRepository.save(attachments);
    }

    /**
     * Update a attachments.
     *
     * @param attachments the entity to save.
     * @return the persisted entity.
     */
    public Attachments update(Attachments attachments) {
        log.debug("Request to update Attachments : {}", attachments);
        return attachmentsRepository.save(attachments);
    }

    /**
     * Partially update a attachments.
     *
     * @param attachments the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Attachments> partialUpdate(Attachments attachments) {
        log.debug("Request to partially update Attachments : {}", attachments);

        return attachmentsRepository
            .findById(attachments.getId())
            .map(existingAttachments -> {
                if (attachments.getAttachName() != null) {
                    existingAttachments.setAttachName(attachments.getAttachName());
                }
                if (attachments.getAttach() != null) {
                    existingAttachments.setAttach(attachments.getAttach());
                }
                if (attachments.getAttachContentType() != null) {
                    existingAttachments.setAttachContentType(attachments.getAttachContentType());
                }

                return existingAttachments;
            })
            .map(attachmentsRepository::save);
    }

    /**
     * Get all the attachments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Attachments> findAll() {
        log.debug("Request to get all Attachments");
        return attachmentsRepository.findAll();
    }

    /**
     * Get one attachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Attachments> findOne(Long id) {
        log.debug("Request to get Attachments : {}", id);
        return attachmentsRepository.findById(id);
    }

    /**
     * Delete the attachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attachments : {}", id);
        attachmentsRepository.deleteById(id);
    }
}
