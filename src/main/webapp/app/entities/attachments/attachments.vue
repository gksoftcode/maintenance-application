<template>
  <div>
    <h2 id="page-heading" data-cy="AttachmentsHeading">
      <span v-text="$t('maintenanceApp.attachments.home.title')" id="attachments-heading">Attachments</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('maintenanceApp.attachments.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AttachmentsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-attachments"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('maintenanceApp.attachments.home.createLabel')"> Create a new Attachments </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && attachments && attachments.length === 0">
      <span v-text="$t('maintenanceApp.attachments.home.notFound')">No attachments found</span>
    </div>
    <div class="table-responsive" v-if="attachments && attachments.length > 0">
      <table class="table table-striped" aria-describedby="attachments">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.attachments.attachName')">Attach Name</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.attachments.attach')">Attach</span></th>
            <th scope="row"><span v-text="$t('maintenanceApp.attachments.workOrdersHistory')">Work Orders History</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attachments in attachments" :key="attachments.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AttachmentsView', params: { attachmentsId: attachments.id } }">{{ attachments.id }}</router-link>
            </td>
            <td>{{ attachments.attachName }}</td>
            <td>
              <a
                v-if="attachments.attach"
                v-on:click="openFile(attachments.attachContentType, attachments.attach)"
                v-text="$t('entity.action.open')"
                >open</a
              >
              <span v-if="attachments.attach">{{ attachments.attachContentType }}, {{ byteSize(attachments.attach) }}</span>
            </td>
            <td>
              <div v-if="attachments.workOrdersHistory">
                <router-link :to="{ name: 'WorkOrdersHistoryView', params: { workOrdersHistoryId: attachments.workOrdersHistory.id } }">{{
                  attachments.workOrdersHistory.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AttachmentsView', params: { attachmentsId: attachments.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AttachmentsEdit', params: { attachmentsId: attachments.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(attachments)"
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
        ><span id="maintenanceApp.attachments.delete.question" data-cy="attachmentsDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-attachments-heading" v-text="$t('maintenanceApp.attachments.delete.question', { id: removeId })">
          Are you sure you want to delete this Attachments?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-attachments"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAttachments()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./attachments.component.ts"></script>
