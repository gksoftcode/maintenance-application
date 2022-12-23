/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import WorkOrdersHistoryUpdateComponent from '@/entities/work-orders-history/work-orders-history-update.vue';
import WorkOrdersHistoryClass from '@/entities/work-orders-history/work-orders-history-update.component';
import WorkOrdersHistoryService from '@/entities/work-orders-history/work-orders-history.service';

import StatusService from '@/entities/status/status.service';

import ActionsService from '@/entities/actions/actions.service';

import AttachmentsService from '@/entities/attachments/attachments.service';

import WorkOrdersService from '@/entities/work-orders/work-orders.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('WorkOrdersHistory Management Update Component', () => {
    let wrapper: Wrapper<WorkOrdersHistoryClass>;
    let comp: WorkOrdersHistoryClass;
    let workOrdersHistoryServiceStub: SinonStubbedInstance<WorkOrdersHistoryService>;

    beforeEach(() => {
      workOrdersHistoryServiceStub = sinon.createStubInstance<WorkOrdersHistoryService>(WorkOrdersHistoryService);

      wrapper = shallowMount<WorkOrdersHistoryClass>(WorkOrdersHistoryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          workOrdersHistoryService: () => workOrdersHistoryServiceStub,
          alertService: () => new AlertService(),

          statusService: () =>
            sinon.createStubInstance<StatusService>(StatusService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          actionsService: () =>
            sinon.createStubInstance<ActionsService>(ActionsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          attachmentsService: () =>
            sinon.createStubInstance<AttachmentsService>(AttachmentsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          workOrdersService: () =>
            sinon.createStubInstance<WorkOrdersService>(WorkOrdersService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.workOrdersHistory = entity;
        workOrdersHistoryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(workOrdersHistoryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.workOrdersHistory = entity;
        workOrdersHistoryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(workOrdersHistoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundWorkOrdersHistory = { id: 123 };
        workOrdersHistoryServiceStub.find.resolves(foundWorkOrdersHistory);
        workOrdersHistoryServiceStub.retrieve.resolves([foundWorkOrdersHistory]);

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
