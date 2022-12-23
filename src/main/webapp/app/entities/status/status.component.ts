import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStatus } from '@/shared/model/status.model';

import StatusService from './status.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Status extends Vue {
  @Inject('statusService') private statusService: () => StatusService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public statuses: IStatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStatuss();
  }

  public clear(): void {
    this.retrieveAllStatuss();
  }

  public retrieveAllStatuss(): void {
    this.isFetching = true;
    this.statusService()
      .retrieve()
      .then(
        res => {
          this.statuses = res.data;
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

  public prepareRemove(instance: IStatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStatus(): void {
    this.statusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('maintenanceApp.status.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStatuss();
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
