/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import WorkOrdersHistoryDetailComponent from '@/entities/work-orders-history/work-orders-history-details.vue';
import WorkOrdersHistoryClass from '@/entities/work-orders-history/work-orders-history-details.component';
import WorkOrdersHistoryService from '@/entities/work-orders-history/work-orders-history.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('WorkOrdersHistory Management Detail Component', () => {
    let wrapper: Wrapper<WorkOrdersHistoryClass>;
    let comp: WorkOrdersHistoryClass;
    let workOrdersHistoryServiceStub: SinonStubbedInstance<WorkOrdersHistoryService>;

    beforeEach(() => {
      workOrdersHistoryServiceStub = sinon.createStubInstance<WorkOrdersHistoryService>(WorkOrdersHistoryService);

      wrapper = shallowMount<WorkOrdersHistoryClass>(WorkOrdersHistoryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { workOrdersHistoryService: () => workOrdersHistoryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundWorkOrdersHistory = { id: 123 };
        workOrdersHistoryServiceStub.find.resolves(foundWorkOrdersHistory);

        // WHEN
        comp.retrieveWorkOrdersHistory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.workOrdersHistory).toBe(foundWorkOrdersHistory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWorkOrdersHistory = { id: 123 };
        workOrdersHistoryServiceStub.find.resolves(foundWorkOrdersHistory);

        // WHEN
        comp.beforeRouteEnter({ params: { workOrdersHistoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.workOrdersHistory).toBe(foundWorkOrdersHistory);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
