/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ActionsUpdateComponent from '@/entities/actions/actions-update.vue';
import ActionsClass from '@/entities/actions/actions-update.component';
import ActionsService from '@/entities/actions/actions.service';

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
  describe('Actions Management Update Component', () => {
    let wrapper: Wrapper<ActionsClass>;
    let comp: ActionsClass;
    let actionsServiceStub: SinonStubbedInstance<ActionsService>;

    beforeEach(() => {
      actionsServiceStub = sinon.createStubInstance<ActionsService>(ActionsService);

      wrapper = shallowMount<ActionsClass>(ActionsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          actionsService: () => actionsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.actions = entity;
        actionsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(actionsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.actions = entity;
        actionsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(actionsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundActions = { id: 123 };
        actionsServiceStub.find.resolves(foundActions);
        actionsServiceStub.retrieve.resolves([foundActions]);

        // WHEN
        comp.beforeRouteEnter({ params: { actionsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.actions).toBe(foundActions);
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
