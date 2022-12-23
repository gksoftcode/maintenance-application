import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWorkOrdersHistory } from '@/shared/model/work-orders-history.model';
import WorkOrdersHistoryService from './work-orders-history.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class WorkOrdersHistoryDetails extends Vue {
  @Inject('workOrdersHistoryService') private workOrdersHistoryService: () => WorkOrdersHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public workOrdersHistory: IWorkOrdersHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workOrdersHistoryId) {
        vm.retrieveWorkOrdersHistory(to.params.workOrdersHistoryId);
      }
    });
  }

  public retrieveWorkOrdersHistory(workOrdersHistoryId) {
    this.workOrdersHistoryService()
      .find(workOrdersHistoryId)
      .then(res => {
        this.workOrdersHistory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
