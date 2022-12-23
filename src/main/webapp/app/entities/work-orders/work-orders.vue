<template>
  <div>
    <h2 id="page-heading" data-cy="WorkOrdersHeading">
      <span v-text="$t('maintenanceApp.workOrders.home.title')" id="work-orders-heading">Work Orders</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('maintenanceApp.workOrders.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'WorkOrdersCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-work-orders"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('maintenanceApp.workOrders.home.createLabel')"> Create a new Work Orders </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && workOrders && workOrders.length === 0">
      <span v-text="$t('maintenanceApp.workOrders.home.notFound')">No workOrders found</span>
    </div>
    <div class="table-responsive" v-if="workOrders && workOrders.length > 0">
      <table class="table table-striped" aria-describedby="workOrders">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.title')">Title</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.description')">Description</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.startDate')">Start Date</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.endDate')">End Date</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.budget')">Budget</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.itemSerial')">Item Serial</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.isWaranty')">Is Waranty</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.notes')">Notes</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.status')">Status</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.client')">Client</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.itemModels')">Item Models</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrders.itemBrand')">Item Brand</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="workOrders in workOrders" :key="workOrders.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'WorkOrdersView', params: { workOrdersId: workOrders.id } }">{{ workOrders.id }}</router-link>
            </td>
            <td>{{ workOrders.title }}</td>
            <td>{{ workOrders.description }}</td>
            <td>{{ workOrders.startDate ? $d(Date.parse(workOrders.startDate), 'short') : '' }}</td>
            <td>{{ workOrders.endDate ? $d(Date.parse(workOrders.endDate), 'short') : '' }}</td>
            <td>{{ workOrders.budget }}</td>
            <td>{{ workOrders.itemSerial }}</td>
            <td>{{ workOrders.isWaranty }}</td>
            <td>{{ workOrders.notes }}</td>
            <td>
              <div v-if="workOrders.status">
                <router-link :to="{ name: 'StatusView', params: { statusId: workOrders.status.id } }">{{
                  workOrders.status.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="workOrders.client">
                <router-link :to="{ name: 'ClientView', params: { clientId: workOrders.client.id } }">{{
                  workOrders.client.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="workOrders.itemModels">
                <router-link :to="{ name: 'ItemModelsView', params: { itemModelsId: workOrders.itemModels.id } }">{{
                  workOrders.itemModels.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="workOrders.itemBrand">
                <router-link :to="{ name: 'ItemBrandView', params: { itemBrandId: workOrders.itemBrand.id } }">{{
                  workOrders.itemBrand.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'WorkOrdersView', params: { workOrdersId: workOrders.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'WorkOrdersEdit', params: { workOrdersId: workOrders.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(workOrders)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="maintenanceApp.workOrders.delete.question" data-cy="workOrdersDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-workOrders-heading" v-text="$t('maintenanceApp.workOrders.delete.question', { id: removeId })">
          Are you sure you want to delete this Work Orders?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-workOrders"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeWorkOrders()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./work-orders.component.ts"></script>
