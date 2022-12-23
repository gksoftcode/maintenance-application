<template>
  <div>
    <h2 id="page-heading" data-cy="WorkOrdersHistoryHeading">
      <span v-text="$t('maintenanceApp.workOrdersHistory.home.title')" id="work-orders-history-heading">Work Orders Histories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('maintenanceApp.workOrdersHistory.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'WorkOrdersHistoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-work-orders-history"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('maintenanceApp.workOrdersHistory.home.createLabel')"> Create a new Work Orders History </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && workOrdersHistories && workOrdersHistories.length === 0">
      <span v-text="$t('maintenanceApp.workOrdersHistory.home.notFound')">No workOrdersHistories found</span>
    </div>
    <div class="table-responsive" v-if="workOrdersHistories && workOrdersHistories.length > 0">
      <table class="table table-striped" aria-describedby="workOrdersHistories">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrdersHistory.isShared')">Is Shared</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrdersHistory.actionDate')">Action Date</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrdersHistory.note')">Note</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrdersHistory.status')">Status</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrdersHistory.action')">Action</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.workOrdersHistory.workOrders')">Work Orders</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="workOrdersHistory in workOrdersHistories" :key="workOrdersHistory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'WorkOrdersHistoryView', params: { workOrdersHistoryId: workOrdersHistory.id } }">{{
                workOrdersHistory.id
              }}</router-link>
            </td>
            <td>{{ workOrdersHistory.isShared }}</td>
            <td>{{ workOrdersHistory.actionDate ? $d(Date.parse(workOrdersHistory.actionDate), 'short') : '' }}</td>
            <td>{{ workOrdersHistory.note }}</td>
            <td>
              <div v-if="workOrdersHistory.status">
                <router-link :to="{ name: 'StatusView', params: { statusId: workOrdersHistory.status.id } }">{{
                  workOrdersHistory.status.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="workOrdersHistory.action">
                <router-link :to="{ name: 'ActionsView', params: { actionsId: workOrdersHistory.action.id } }">{{
                  workOrdersHistory.action.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="workOrdersHistory.workOrders">
                <router-link :to="{ name: 'WorkOrdersView', params: { workOrdersId: workOrdersHistory.workOrders.id } }">{{
                  workOrdersHistory.workOrders.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'WorkOrdersHistoryView', params: { workOrdersHistoryId: workOrdersHistory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'WorkOrdersHistoryEdit', params: { workOrdersHistoryId: workOrdersHistory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(workOrdersHistory)"
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
        ><span
          id="maintenanceApp.workOrdersHistory.delete.question"
          data-cy="workOrdersHistoryDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-workOrdersHistory-heading" v-text="$t('maintenanceApp.workOrdersHistory.delete.question', { id: removeId })">
          Are you sure you want to delete this Work Orders History?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-workOrdersHistory"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeWorkOrdersHistory()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./work-orders-history.component.ts"></script>
