package com.gksoft.maintenance.repository;

import com.gksoft.maintenance.domain.Actions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Actions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionsRepository extends JpaRepository<Actions, Long> {}
