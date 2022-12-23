package com.gksoft.maintenance.repository;

import com.gksoft.maintenance.domain.ItemBrand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemBrandRepository extends JpaRepository<ItemBrand, Long> {}
