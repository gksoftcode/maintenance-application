import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import StatusService from '@/entities/status/status.service';
import { IStatus } from '@/shared/model/status.model';

import ActionsService from '@/entities/actions/actions.service';
import { IActions } from '@/shared/model/actions.model';

import AttachmentsService from '@/entities/attachments/attachments.service';
import { IAttachments } from '@/shared/model/attachments.model';

import WorkOrdersService from '@/entities/work-orders/work-orders.service';
import { IWorkOrders } from '@/shared/model/work-orders.model';

import { IWorkOrdersHistory, WorkOrdersHistory } from '@/shared/model/work-orders-history.model';
import WorkOrdersHistoryService from './work-orders-history.service';

const validations: any = {
  workOrdersHistory: {
    isShared: {},
    actionDate: {},
    note: {},
  },
};

@Component({
  validations,
})
export default class WorkOrdersHistoryUpdate extends Vue {
  @Inject('workOrdersHistoryService') private workOrdersHistoryService: () => WorkOrdersHistoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public workOrdersHistory: IWorkOrdersHistory = new WorkOrdersHistory();

  @Inject('statusService') private statusService: () => StatusService;

  public statuses: IStatus[] = [];

  @Inject('actionsService') private actionsService: () => ActionsService;

  public actions: IActions[] = [];

  @Inject('attachmentsService') private attachmentsService: () => AttachmentsService;

  public attachments: IAttachments[] = [];

  @Inject('workOrdersService') private workOrdersService: () => WorkOrdersService;

  public workOrders: IWorkOrders[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workOrdersHistoryId) {
        vm.retrieveWorkOrdersHistory(to.params.workOrdersHistoryId);
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
    if (this.workOrdersHistory.id) {
      this.workOrdersHistoryService()
        .update(this.workOrdersHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('maintenanceApp.workOrdersHistory.updated', { param: param.id });
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
      this.workOrdersHistoryService()
        .create(this.workOrdersHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('maintenanceApp.workOrdersHistory.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.workOrdersHistory[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.workOrdersHistory[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.workOrdersHistory[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.workOrdersHistory[field] = null;
    }
  }

  public retrieveWorkOrdersHistory(workOrdersHistoryId): void {
    this.workOrdersHistoryService()
      .find(workOrdersHistoryId)
      .then(res => {
        res.actionDate = new Date(res.actionDate);
        this.workOrdersHistory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.statusService()
      .retrieve()
      .then(res => {
        this.statuses = res.data;
      });
    this.actionsService()
      .retrieve()
      .then(res => {
        this.actions = res.data;
      });
    this.attachmentsService()
      .retrieve()
      .then(res => {
        this.attachments = res.data;
      });
    this.workOrdersService()
      .retrieve()
      .then(res => {
        this.workOrders = res.data;
      });
  }
}
