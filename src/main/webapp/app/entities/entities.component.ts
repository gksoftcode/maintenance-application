import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import RegionService from './region/region.service';
import CountryService from './country/country.service';
import LocationService from './location/location.service';
import DepartmentService from './department/department.service';
import TaskService from './task/task.service';
import EmployeeService from './employee/employee.service';
import JobService from './job/job.service';
import JobHistoryService from './job-history/job-history.service';
import ClientService from './client/client.service';
import AppintmentService from './appintment/appintment.service';
import ActionsService from './actions/actions.service';
import ItemModelsService from './item-models/item-models.service';
import ItemBrandService from './item-brand/item-brand.service';
import StatusService from './status/status.service';
import WorkOrdersService from './work-orders/work-orders.service';
import WorkOrdersHistoryService from './work-orders-history/work-orders-history.service';
import AttachmentsService from './attachments/attachments.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('regionService') private regionService = () => new RegionService();
  @Provide('countryService') private countryService = () => new CountryService();
  @Provide('locationService') private locationService = () => new LocationService();
  @Provide('departmentService') private departmentService = () => new DepartmentService();
  @Provide('taskService') private taskService = () => new TaskService();
  @Provide('employeeService') private employeeService = () => new EmployeeService();
  @Provide('jobService') private jobService = () => new JobService();
  @Provide('jobHistoryService') private jobHistoryService = () => new JobHistoryService();
  @Provide('clientService') private clientService = () => new ClientService();
  @Provide('appintmentService') private appintmentService = () => new AppintmentService();
  @Provide('actionsService') private actionsService = () => new ActionsService();
  @Provide('itemModelsService') private itemModelsService = () => new ItemModelsService();
  @Provide('itemBrandService') private itemBrandService = () => new ItemBrandService();
  @Provide('statusService') private statusService = () => new StatusService();
  @Provide('workOrdersService') private workOrdersService = () => new WorkOrdersService();
  @Provide('workOrdersHistoryService') private workOrdersHistoryService = () => new WorkOrdersHistoryService();
  @Provide('attachmentsService') private attachmentsService = () => new AttachmentsService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
