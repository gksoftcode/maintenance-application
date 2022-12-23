<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="maintenanceApp.workOrdersHistory.home.createOrEditLabel"
          data-cy="WorkOrdersHistoryCreateUpdateHeading"
          v-text="$t('maintenanceApp.workOrdersHistory.home.createOrEditLabel')"
        >
          Create or edit a WorkOrdersHistory
        </h2>
        <div>
          <div class="form-group" v-if="workOrdersHistory.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="workOrdersHistory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('maintenanceApp.workOrdersHistory.isShared')" for="work-orders-history-isShared"
              >Is Shared</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="isShared"
              id="work-orders-history-isShared"
              data-cy="isShared"
              :class="{ valid: !$v.workOrdersHistory.isShared.$invalid, invalid: $v.workOrdersHistory.isShared.$invalid }"
              v-model="$v.workOrdersHistory.isShared.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('maintenanceApp.workOrdersHistory.actionDate')"
              for="work-orders-history-actionDate"
              >Action Date</label
            >
            <div class="d-flex">
              <input
                id="work-orders-history-actionDate"
                data-cy="actionDate"
                type="datetime-local"
                class="form-control"
                name="actionDate"
                :class="{ valid: !$v.workOrdersHistory.actionDate.$invalid, invalid: $v.workOrdersHistory.actionDate.$invalid }"
                :value="convertDateTimeFromServer($v.workOrdersHistory.actionDate.$model)"
                @change="updateInstantField('actionDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('maintenanceApp.workOrdersHistory.note')" for="work-orders-history-note"
              >Note</label
            >
            <input
              type="text"
              class="form-control"
              name="note"
              id="work-orders-history-note"
              data-cy="note"
              :class="{ valid: !$v.workOrdersHistory.note.$invalid, invalid: $v.workOrdersHistory.note.$invalid }"
              v-model="$v.workOrdersHistory.note.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('maintenanceApp.workOrdersHistory.status')" for="work-orders-history-status"
              >Status</label
            >
            <select class="form-control" id="work-orders-history-status" data-cy="status" name="status" v-model="workOrdersHistory.status">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workOrdersHistory.status && statusOption.id === workOrdersHistory.status.id ? workOrdersHistory.status : statusOption
                "
                v-for="statusOption in statuses"
                :key="statusOption.id"
              >
                {{ statusOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('maintenanceApp.workOrdersHistory.action')" for="work-orders-history-action"
              >Action</label
            >
            <select class="form-control" id="work-orders-history-action" data-cy="action" name="action" v-model="workOrdersHistory.action">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workOrdersHistory.action && actionsOption.id === workOrdersHistory.action.id ? workOrdersHistory.action : actionsOption
                "
                v-for="actionsOption in actions"
                :key="actionsOption.id"
              >
                {{ actionsOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('maintenanceApp.workOrdersHistory.workOrders')"
              for="work-orders-history-workOrders"
              >Work Orders</label
            >
            <select
              class="form-control"
              id="work-orders-history-workOrders"
              data-cy="workOrders"
              name="workOrders"
              v-model="workOrdersHistory.workOrders"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workOrdersHistory.workOrders && workOrdersOption.id === workOrdersHistory.workOrders.id
                    ? workOrdersHistory.workOrders
                    : workOrdersOption
                "
                v-for="workOrdersOption in workOrders"
                :key="workOrdersOption.id"
              >
                {{ workOrdersOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.workOrdersHistory.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./work-orders-history-update.component.ts"></script>
