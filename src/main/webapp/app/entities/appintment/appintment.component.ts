import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAppintment } from '@/shared/model/appintment.model';

import AppintmentService from './appintment.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Appintment extends Vue {
  @Inject('appintmentService') private appintmentService: () => AppintmentService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public appintments: IAppintment[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAppintments();
  }

  public clear(): void {
    this.retrieveAllAppintments();
  }

  public retrieveAllAppintments(): void {
    this.isFetching = true;
    this.appintmentService()
      .retrieve()
      .then(
        res => {
          this.appintments = res.data;
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

  public prepareRemove(instance: IAppintment): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAppintment(): void {
    this.appintmentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('maintenanceApp.appintment.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAppintments();
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
