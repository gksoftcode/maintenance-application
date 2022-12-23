import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Region = () => import('@/entities/region/region.vue');
// prettier-ignore
const RegionUpdate = () => import('@/entities/region/region-update.vue');
// prettier-ignore
const RegionDetails = () => import('@/entities/region/region-details.vue');
// prettier-ignore
const Country = () => import('@/entities/country/country.vue');
// prettier-ignore
const CountryUpdate = () => import('@/entities/country/country-update.vue');
// prettier-ignore
const CountryDetails = () => import('@/entities/country/country-details.vue');
// prettier-ignore
const Location = () => import('@/entities/location/location.vue');
// prettier-ignore
const LocationUpdate = () => import('@/entities/location/location-update.vue');
// prettier-ignore
const LocationDetails = () => import('@/entities/location/location-details.vue');
// prettier-ignore
const Department = () => import('@/entities/department/department.vue');
// prettier-ignore
const DepartmentUpdate = () => import('@/entities/department/department-update.vue');
// prettier-ignore
const DepartmentDetails = () => import('@/entities/department/department-details.vue');
// prettier-ignore
const Task = () => import('@/entities/task/task.vue');
// prettier-ignore
const TaskUpdate = () => import('@/entities/task/task-update.vue');
// prettier-ignore
const TaskDetails = () => import('@/entities/task/task-details.vue');
// prettier-ignore
const Employee = () => import('@/entities/employee/employee.vue');
// prettier-ignore
const EmployeeUpdate = () => import('@/entities/employee/employee-update.vue');
// prettier-ignore
const EmployeeDetails = () => import('@/entities/employee/employee-details.vue');
// prettier-ignore
const Job = () => import('@/entities/job/job.vue');
// prettier-ignore
const JobUpdate = () => import('@/entities/job/job-update.vue');
// prettier-ignore
const JobDetails = () => import('@/entities/job/job-details.vue');
// prettier-ignore
const JobHistory = () => import('@/entities/job-history/job-history.vue');
// prettier-ignore
const JobHistoryUpdate = () => import('@/entities/job-history/job-history-update.vue');
// prettier-ignore
const JobHistoryDetails = () => import('@/entities/job-history/job-history-details.vue');
// prettier-ignore
const Client = () => import('@/entities/client/client.vue');
// prettier-ignore
const ClientUpdate = () => import('@/entities/client/client-update.vue');
// prettier-ignore
const ClientDetails = () => import('@/entities/client/client-details.vue');
// prettier-ignore
const Appintment = () => import('@/entities/appintment/appintment.vue');
// prettier-ignore
const AppintmentUpdate = () => import('@/entities/appintment/appintment-update.vue');
// prettier-ignore
const AppintmentDetails = () => import('@/entities/appintment/appintment-details.vue');
// prettier-ignore
const Actions = () => import('@/entities/actions/actions.vue');
// prettier-ignore
const ActionsUpdate = () => import('@/entities/actions/actions-update.vue');
// prettier-ignore
const ActionsDetails = () => import('@/entities/actions/actions-details.vue');
// prettier-ignore
const ItemModels = () => import('@/entities/item-models/item-models.vue');
// prettier-ignore
const ItemModelsUpdate = () => import('@/entities/item-models/item-models-update.vue');
// prettier-ignore
const ItemModelsDetails = () => import('@/entities/item-models/item-models-details.vue');
// prettier-ignore
const ItemBrand = () => import('@/entities/item-brand/item-brand.vue');
// prettier-ignore
const ItemBrandUpdate = () => import('@/entities/item-brand/item-brand-update.vue');
// prettier-ignore
const ItemBrandDetails = () => import('@/entities/item-brand/item-brand-details.vue');
// prettier-ignore
const Status = () => import('@/entities/status/status.vue');
// prettier-ignore
const StatusUpdate = () => import('@/entities/status/status-update.vue');
// prettier-ignore
const StatusDetails = () => import('@/entities/status/status-details.vue');
// prettier-ignore
const WorkOrders = () => import('@/entities/work-orders/work-orders.vue');
// prettier-ignore
const WorkOrdersUpdate = () => import('@/entities/work-orders/work-orders-update.vue');
// prettier-ignore
const WorkOrdersDetails = () => import('@/entities/work-orders/work-orders-details.vue');
// prettier-ignore
const WorkOrdersHistory = () => import('@/entities/work-orders-history/work-orders-history.vue');
// prettier-ignore
const WorkOrdersHistoryUpdate = () => import('@/entities/work-orders-history/work-orders-history-update.vue');
// prettier-ignore
const WorkOrdersHistoryDetails = () => import('@/entities/work-orders-history/work-orders-history-details.vue');
// prettier-ignore
const Attachments = () => import('@/entities/attachments/attachments.vue');
// prettier-ignore
const AttachmentsUpdate = () => import('@/entities/attachments/attachments-update.vue');
// prettier-ignore
const AttachmentsDetails = () => import('@/entities/attachments/attachments-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'region',
      name: 'Region',
      component: Region,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/new',
      name: 'RegionCreate',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/edit',
      name: 'RegionEdit',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/view',
      name: 'RegionView',
      component: RegionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location',
      name: 'Location',
      component: Location,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location/new',
      name: 'LocationCreate',
      component: LocationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location/:locationId/edit',
      name: 'LocationEdit',
      component: LocationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'location/:locationId/view',
      name: 'LocationView',
      component: LocationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department',
      name: 'Department',
      component: Department,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/new',
      name: 'DepartmentCreate',
      component: DepartmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/:departmentId/edit',
      name: 'DepartmentEdit',
      component: DepartmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'department/:departmentId/view',
      name: 'DepartmentView',
      component: DepartmentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task',
      name: 'Task',
      component: Task,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task/new',
      name: 'TaskCreate',
      component: TaskUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task/:taskId/edit',
      name: 'TaskEdit',
      component: TaskUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'task/:taskId/view',
      name: 'TaskView',
      component: TaskDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee',
      name: 'Employee',
      component: Employee,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/new',
      name: 'EmployeeCreate',
      component: EmployeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/:employeeId/edit',
      name: 'EmployeeEdit',
      component: EmployeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'employee/:employeeId/view',
      name: 'EmployeeView',
      component: EmployeeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job',
      name: 'Job',
      component: Job,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/new',
      name: 'JobCreate',
      component: JobUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/:jobId/edit',
      name: 'JobEdit',
      component: JobUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/:jobId/view',
      name: 'JobView',
      component: JobDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history',
      name: 'JobHistory',
      component: JobHistory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history/new',
      name: 'JobHistoryCreate',
      component: JobHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history/:jobHistoryId/edit',
      name: 'JobHistoryEdit',
      component: JobHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job-history/:jobHistoryId/view',
      name: 'JobHistoryView',
      component: JobHistoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client',
      name: 'Client',
      component: Client,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/new',
      name: 'ClientCreate',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/edit',
      name: 'ClientEdit',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/view',
      name: 'ClientView',
      component: ClientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appintment',
      name: 'Appintment',
      component: Appintment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appintment/new',
      name: 'AppintmentCreate',
      component: AppintmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appintment/:appintmentId/edit',
      name: 'AppintmentEdit',
      component: AppintmentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appintment/:appintmentId/view',
      name: 'AppintmentView',
      component: AppintmentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'actions',
      name: 'Actions',
      component: Actions,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'actions/new',
      name: 'ActionsCreate',
      component: ActionsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'actions/:actionsId/edit',
      name: 'ActionsEdit',
      component: ActionsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'actions/:actionsId/view',
      name: 'ActionsView',
      component: ActionsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-models',
      name: 'ItemModels',
      component: ItemModels,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-models/new',
      name: 'ItemModelsCreate',
      component: ItemModelsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-models/:itemModelsId/edit',
      name: 'ItemModelsEdit',
      component: ItemModelsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-models/:itemModelsId/view',
      name: 'ItemModelsView',
      component: ItemModelsDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-brand',
      name: 'ItemBrand',
      component: ItemBrand,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-brand/new',
      name: 'ItemBrandCreate',
      component: ItemBrandUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-brand/:itemBrandId/edit',
      name: 'ItemBrandEdit',
      component: ItemBrandUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item-brand/:itemBrandId/view',
      name: 'ItemBrandView',
      component: ItemBrandDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status',
      name: 'Status',
      component: Status,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status/new',
      name: 'StatusCreate',
      component: StatusUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status/:statusId/edit',
      name: 'StatusEdit',
      component: StatusUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'status/:statusId/view',
      name: 'StatusView',
      component: StatusDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders',
      name: 'WorkOrders',
      component: WorkOrders,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders/new',
      name: 'WorkOrdersCreate',
      component: WorkOrdersUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders/:workOrdersId/edit',
      name: 'WorkOrdersEdit',
      component: WorkOrdersUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders/:workOrdersId/view',
      name: 'WorkOrdersView',
      component: WorkOrdersDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders-history',
      name: 'WorkOrdersHistory',
      component: WorkOrdersHistory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders-history/new',
      name: 'WorkOrdersHistoryCreate',
      component: WorkOrdersHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders-history/:workOrdersHistoryId/edit',
      name: 'WorkOrdersHistoryEdit',
      component: WorkOrdersHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'work-orders-history/:workOrdersHistoryId/view',
      name: 'WorkOrdersHistoryView',
      component: WorkOrdersHistoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attachments',
      name: 'Attachments',
      component: Attachments,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attachments/new',
      name: 'AttachmentsCreate',
      component: AttachmentsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attachments/:attachmentsId/edit',
      name: 'AttachmentsEdit',
      component: AttachmentsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'attachments/:attachmentsId/view',
      name: 'AttachmentsView',
      component: AttachmentsDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
