package com.gksoft.maintenance.service;

import com.gksoft.maintenance.domain.ItemBrand;
import com.gksoft.maintenance.repository.ItemBrandRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemBrand}.
 */
@Service
@Transactional
public class ItemBrandService {

    private final Logger log = LoggerFactory.getLogger(ItemBrandService.class);

    private final ItemBrandRepository itemBrandRepository;

    public ItemBrandService(ItemBrandRepository itemBrandRepository) {
        this.itemBrandRepository = itemBrandRepository;
    }

    /**
     * Save a itemBrand.
     *
     * @param itemBrand the entity to save.
     * @return the persisted entity.
     */
    public ItemBrand save(ItemBrand itemBrand) {
        log.debug("Request to save ItemBrand : {}", itemBrand);
        return itemBrandRepository.save(itemBrand);
    }

    /**
     * Update a itemBrand.
     *
     * @param itemBrand the entity to save.
     * @return the persisted entity.
     */
    public ItemBrand update(ItemBrand itemBrand) {
        log.debug("Request to update ItemBrand : {}", itemBrand);
        return itemBrandRepository.save(itemBrand);
    }

    /**
     * Partially update a itemBrand.
     *
     * @param itemBrand the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ItemBrand> partialUpdate(ItemBrand itemBrand) {
        log.debug("Request to partially update ItemBrand : {}", itemBrand);

        return itemBrandRepository
            .findById(itemBrand.getId())
            .map(existingItemBrand -> {
                if (itemBrand.getBrandName() != null) {
                    existingItemBrand.setBrandName(itemBrand.getBrandName());
                }

                return existingItemBrand;
            })
            .map(itemBrandRepository::save);
    }

    /**
     * Get all the itemBrands.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ItemBrand> findAll() {
        log.debug("Request to get all ItemBrands");
        return itemBrandRepository.findAll();
    }

    /**
     * Get one itemBrand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemBrand> findOne(Long id) {
        log.debug("Request to get ItemBrand : {}", id);
        return itemBrandRepository.findById(id);
    }

    /**
     * Delete the itemBrand by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemBrand : {}", id);
        itemBrandRepository.deleteById(id);
    }
}
