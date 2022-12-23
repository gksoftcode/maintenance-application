package com.gksoft.maintenance.repository;

import com.gksoft.maintenance.domain.WorkOrdersHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkOrdersHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkOrdersHistoryRepository extends JpaRepository<WorkOrdersHistory, Long> {}
