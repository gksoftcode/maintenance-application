/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ItemBrandComponent from '@/entities/item-brand/item-brand.vue';
import ItemBrandClass from '@/entities/item-brand/item-brand.component';
import ItemBrandService from '@/entities/item-brand/item-brand.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ItemBrand Management Component', () => {
    let wrapper: Wrapper<ItemBrandClass>;
    let comp: ItemBrandClass;
    let itemBrandServiceStub: SinonStubbedInstance<ItemBrandService>;

    beforeEach(() => {
      itemBrandServiceStub = sinon.createStubInstance<ItemBrandService>(ItemBrandService);
      itemBrandServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ItemBrandClass>(ItemBrandComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          itemBrandService: () => itemBrandServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      itemBrandServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllItemBrands();
      await comp.$nextTick();

      // THEN
      expect(itemBrandServiceStub.retrieve.called).toBeTruthy();
      expect(comp.itemBrands[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      itemBrandServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(itemBrandServiceStub.retrieve.callCount).toEqual(1);

      comp.removeItemBrand();
      await comp.$nextTick();

      // THEN
      expect(itemBrandServiceStub.delete.called).toBeTruthy();
      expect(itemBrandServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
