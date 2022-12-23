/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ActionsComponent from '@/entities/actions/actions.vue';
import ActionsClass from '@/entities/actions/actions.component';
import ActionsService from '@/entities/actions/actions.service';
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
  describe('Actions Management Component', () => {
    let wrapper: Wrapper<ActionsClass>;
    let comp: ActionsClass;
    let actionsServiceStub: SinonStubbedInstance<ActionsService>;

    beforeEach(() => {
      actionsServiceStub = sinon.createStubInstance<ActionsService>(ActionsService);
      actionsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ActionsClass>(ActionsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          actionsService: () => actionsServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      actionsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllActionss();
      await comp.$nextTick();

      // THEN
      expect(actionsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.actions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      actionsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(actionsServiceStub.retrieve.callCount).toEqual(1);

      comp.removeActions();
      await comp.$nextTick();

      // THEN
      expect(actionsServiceStub.delete.called).toBeTruthy();
      expect(actionsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
