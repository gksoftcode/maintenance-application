import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IItemModels } from '@/shared/model/item-models.model';

import ItemModelsService from './item-models.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ItemModels extends Vue {
  @Inject('itemModelsService') private itemModelsService: () => ItemModelsService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public itemModels: IItemModels[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllItemModelss();
  }

  public clear(): void {
    this.retrieveAllItemModelss();
  }

  public retrieveAllItemModelss(): void {
    this.isFetching = true;
    this.itemModelsService()
      .retrieve()
      .then(
        res => {
          this.itemModels = res.data;
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

  public prepareRemove(instance: IItemModels): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeItemModels(): void {
    this.itemModelsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('maintenanceApp.itemModels.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllItemModelss();
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
