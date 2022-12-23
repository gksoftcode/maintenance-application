import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AlertService from '@/shared/alert/alert.service';

import WorkOrdersHistoryService from '@/entities/work-orders-history/work-orders-history.service';
import { IWorkOrdersHistory } from '@/shared/model/work-orders-history.model';

import { IAttachments, Attachments } from '@/shared/model/attachments.model';
import AttachmentsService from './attachments.service';

const validations: any = {
  attachments: {
    attachName: {},
    attach: {},
  },
};

@Component({
  validations,
})
export default class AttachmentsUpdate extends mixins(JhiDataUtils) {
  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;
  @Inject('alertService') private alertService: () => AlertService;

  public attachments: IAttachments = new Attachments();

  @Inject('workOrdersHistoryService') private workOrdersHistoryService: () => WorkOrdersHistoryService;

  public workOrdersHistories: IWorkOrdersHistory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attachmentsId) {
        vm.retrieveAttachments(to.params.attachmentsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.attachments.id) {
      this.attachmentsService()
        .update(this.attachments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('maintenanceApp.attachments.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.attachmentsService()
        .create(this.attachments)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('maintenanceApp.attachments.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveAttachments(attachmentsId): void {
    this.attachmentsService()
      .find(attachmentsId)
      .then(res => {
        this.attachments = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.workOrdersHistoryService()
      .retrieve()
      .then(res => {
        this.workOrdersHistories = res.data;
      });
  }
}
