/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import WorkOrdersComponent from '@/entities/work-orders/work-orders.vue';
import WorkOrdersClass from '@/entities/work-orders/work-orders.component';
import WorkOrdersService from '@/entities/work-orders/work-orders.service';
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
  describe('WorkOrders Management Component', () => {
    let wrapper: Wrapper<WorkOrdersClass>;
    let comp: WorkOrdersClass;
    let workOrdersServiceStub: SinonStubbedInstance<WorkOrdersService>;

    beforeEach(() => {
      workOrdersServiceStub = sinon.createStubInstance<WorkOrdersService>(WorkOrdersService);
      workOrdersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<WorkOrdersClass>(WorkOrdersComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          workOrdersService: () => workOrdersServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      workOrdersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllWorkOrderss();
      await comp.$nextTick();

      // THEN
      expect(workOrdersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.workOrders[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      workOrdersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(workOrdersServiceStub.retrieve.callCount).toEqual(1);

      comp.removeWorkOrders();
      await comp.$nextTick();

      // THEN
      expect(workOrdersServiceStub.delete.called).toBeTruthy();
      expect(workOrdersServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
