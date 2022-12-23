import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAttachments } from '@/shared/model/attachments.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import AttachmentsService from './attachments.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Attachments extends mixins(JhiDataUtils) {
  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public attachments: IAttachments[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAttachmentss();
  }

  public clear(): void {
    this.retrieveAllAttachmentss();
  }

  public retrieveAllAttachmentss(): void {
    this.isFetching = true;
    this.attachmentsService()
      .retrieve()
      .then(
        res => {
          this.attachments = res.data;
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

  public prepareRemove(instance: IAttachments): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAttachments(): void {
    this.attachmentsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('maintenanceApp.attachments.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAttachmentss();
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
