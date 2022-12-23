package com.gksoft.maintenance.repository;

import com.gksoft.maintenance.domain.ItemModels;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemModels entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemModelsRepository extends JpaRepository<ItemModels, Long> {}
