/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StatusComponent from '@/entities/status/status.vue';
import StatusClass from '@/entities/status/status.component';
import StatusService from '@/entities/status/status.service';
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
  describe('Status Management Component', () => {
    let wrapper: Wrapper<StatusClass>;
    let comp: StatusClass;
    let statusServiceStub: SinonStubbedInstance<StatusService>;

    beforeEach(() => {
      statusServiceStub = sinon.createStubInstance<StatusService>(StatusService);
      statusServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StatusClass>(StatusComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          statusService: () => statusServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      statusServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStatuss();
      await comp.$nextTick();

      // THEN
      expect(statusServiceStub.retrieve.called).toBeTruthy();
      expect(comp.statuses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      statusServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(statusServiceStub.retrieve.callCount).toEqual(1);

      comp.removeStatus();
      await comp.$nextTick();

      // THEN
      expect(statusServiceStub.delete.called).toBeTruthy();
      expect(statusServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
