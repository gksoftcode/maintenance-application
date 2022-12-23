import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IActions, Actions } from '@/shared/model/actions.model';
import ActionsService from './actions.service';

const validations: any = {
  actions: {
    actionName: {},
  },
};

@Component({
  validations,
})
export default class ActionsUpdate extends Vue {
  @Inject('actionsService') private actionsService: () => ActionsService;
  @Inject('alertService') private alertService: () => AlertService;

  public actions: IActions = new Actions();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.actionsId) {
        vm.retrieveActions(to.params.actionsId);
      }
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
    if (this.actions.id) {
      this.actionsService()
        .update(this.actions)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('maintenanceApp.actions.updated', { param: param.id });
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
      this.actionsService()
        .create(this.actions)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('maintenanceApp.actions.created', { param: param.id });
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

  public retrieveActions(actionsId): void {
    this.actionsService()
      .find(actionsId)
      .then(res => {
        this.actions = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
