import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IWorkOrdersHistory } from '@/shared/model/work-orders-history.model';

import WorkOrdersHistoryService from './work-orders-history.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class WorkOrdersHistory extends Vue {
  @Inject('workOrdersHistoryService') private workOrdersHistoryService: () => WorkOrdersHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public workOrdersHistories: IWorkOrdersHistory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllWorkOrdersHistorys();
  }

  public clear(): void {
    this.retrieveAllWorkOrdersHistorys();
  }

  public retrieveAllWorkOrdersHistorys(): void {
    this.isFetching = true;
    this.workOrdersHistoryService()
      .retrieve()
      .then(
        res => {
          this.workOrdersHistories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IWorkOrdersHistory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeWorkOrdersHistory(): void {
    this.workOrdersHistoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('maintenanceApp.workOrdersHistory.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllWorkOrdersHistorys();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
