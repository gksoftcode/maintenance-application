/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ActionsDetailComponent from '@/entities/actions/actions-details.vue';
import ActionsClass from '@/entities/actions/actions-details.component';
import ActionsService from '@/entities/actions/actions.service';
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
  describe('Actions Management Detail Component', () => {
    let wrapper: Wrapper<ActionsClass>;
    let comp: ActionsClass;
    let actionsServiceStub: SinonStubbedInstance<ActionsService>;

    beforeEach(() => {
      actionsServiceStub = sinon.createStubInstance<ActionsService>(ActionsService);

      wrapper = shallowMount<ActionsClass>(ActionsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { actionsService: () => actionsServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundActions = { id: 123 };
        actionsServiceStub.find.resolves(foundActions);

        // WHEN
        comp.retrieveActions(123);
        await comp.$nextTick();

        // THEN
        expect(comp.actions).toBe(foundActions);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundActions = { id: 123 };
        actionsServiceStub.find.resolves(foundActions);

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
