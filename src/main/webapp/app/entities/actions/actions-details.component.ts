import { Component, Vue, Inject } from 'vue-property-decorator';

import { IActions } from '@/shared/model/actions.model';
import ActionsService from './actions.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ActionsDetails extends Vue {
  @Inject('actionsService') private actionsService: () => ActionsService;
  @Inject('alertService') private alertService: () => AlertService;

  public actions: IActions = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.actionsId) {
        vm.retrieveActions(to.params.actionsId);
      }
    });
  }

  public retrieveActions(actionsId) {
    this.actionsService()
      .find(actionsId)
      .then(res => {
        this.actions = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
