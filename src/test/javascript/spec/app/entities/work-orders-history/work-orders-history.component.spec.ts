/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import WorkOrdersHistoryComponent from '@/entities/work-orders-history/work-orders-history.vue';
import WorkOrdersHistoryClass from '@/entities/work-orders-history/work-orders-history.component';
import WorkOrdersHistoryService from '@/entities/work-orders-history/work-orders-history.service';
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
  describe('WorkOrdersHistory Management Component', () => {
    let wrapper: Wrapper<WorkOrdersHistoryClass>;
    let comp: WorkOrdersHistoryClass;
    let workOrdersHistoryServiceStub: SinonStubbedInstance<WorkOrdersHistoryService>;

    beforeEach(() => {
      workOrdersHistoryServiceStub = sinon.createStubInstance<WorkOrdersHistoryService>(WorkOrdersHistoryService);
      workOrdersHistoryServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<WorkOrdersHistoryClass>(WorkOrdersHistoryComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          workOrdersHistoryService: () => workOrdersHistoryServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      workOrdersHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllWorkOrdersHistorys();
      await comp.$nextTick();

      // THEN
      expect(workOrdersHistoryServiceStub.retrieve.called).toBeTruthy();
      expect(comp.workOrdersHistories[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      workOrdersHistoryServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(workOrdersHistoryServiceStub.retrieve.callCount).toEqual(1);

      comp.removeWorkOrdersHistory();
      await comp.$nextTick();

      // THEN
      expect(workOrdersHistoryServiceStub.delete.called).toBeTruthy();
      expect(workOrdersHistoryServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
