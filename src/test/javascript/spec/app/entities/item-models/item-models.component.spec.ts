/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ItemModelsComponent from '@/entities/item-models/item-models.vue';
import ItemModelsClass from '@/entities/item-models/item-models.component';
import ItemModelsService from '@/entities/item-models/item-models.service';
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
  describe('ItemModels Management Component', () => {
    let wrapper: Wrapper<ItemModelsClass>;
    let comp: ItemModelsClass;
    let itemModelsServiceStub: SinonStubbedInstance<ItemModelsService>;

    beforeEach(() => {
      itemModelsServiceStub = sinon.createStubInstance<ItemModelsService>(ItemModelsService);
      itemModelsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ItemModelsClass>(ItemModelsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          itemModelsService: () => itemModelsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      itemModelsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllItemModelss();
      await comp.$nextTick();

      // THEN
      expect(itemModelsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.itemModels[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      itemModelsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(itemModelsServiceStub.retrieve.callCount).toEqual(1);

      comp.removeItemModels();
      await comp.$nextTick();

      // THEN
      expect(itemModelsServiceStub.delete.called).toBeTruthy();
      expect(itemModelsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
