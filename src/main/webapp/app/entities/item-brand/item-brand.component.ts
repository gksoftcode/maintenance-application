import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IItemBrand } from '@/shared/model/item-brand.model';

import ItemBrandService from './item-brand.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ItemBrand extends Vue {
  @Inject('itemBrandService') private itemBrandService: () => ItemBrandService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public itemBrands: IItemBrand[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllItemBrands();
  }

  public clear(): void {
    this.retrieveAllItemBrands();
  }

  public retrieveAllItemBrands(): void {
    this.isFetching = true;
    this.itemBrandService()
      .retrieve()
      .then(
        res => {
          this.itemBrands = res.data;
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

  public prepareRemove(instance: IItemBrand): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeItemBrand(): void {
    this.itemBrandService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('maintenanceApp.itemBrand.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllItemBrands();
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
