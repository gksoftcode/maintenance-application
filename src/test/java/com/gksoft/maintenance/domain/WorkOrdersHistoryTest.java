package com.gksoft.maintenance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gksoft.maintenance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkOrdersHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOrdersHistory.class);
        WorkOrdersHistory workOrdersHistory1 = new WorkOrdersHistory();
        workOrdersHistory1.setId(1L);
        WorkOrdersHistory workOrdersHistory2 = new WorkOrdersHistory();
        workOrdersHistory2.setId(workOrdersHistory1.getId());
        assertThat(workOrdersHistory1).isEqualTo(workOrdersHistory2);
        workOrdersHistory2.setId(2L);
        assertThat(workOrdersHistory1).isNotEqualTo(workOrdersHistory2);
        workOrdersHistory1.setId(null);
        assertThat(workOrdersHistory1).isNotEqualTo(workOrdersHistory2);
    }
}
