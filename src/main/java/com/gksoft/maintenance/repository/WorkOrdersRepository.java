package com.gksoft.maintenance.repository;

import com.gksoft.maintenance.domain.WorkOrders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkOrders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkOrdersRepository extends JpaRepository<WorkOrders, Long> {}
