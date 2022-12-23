<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="workOrdersHistory">
        <h2 class="jh-entity-heading" data-cy="workOrdersHistoryDetailsHeading">
          <span v-text="$t('maintenanceApp.workOrdersHistory.detail.title')">WorkOrdersHistory</span> {{ workOrdersHistory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('maintenanceApp.workOrdersHistory.isShared')">Is Shared</span>
          </dt>
          <dd>
            <span>{{ workOrdersHistory.isShared }}</span>
          </dd>
          <dt>
            <span v-text="$t('maintenanceApp.workOrdersHistory.actionDate')">Action Date</span>
          </dt>
          <dd>
            <span v-if="workOrdersHistory.actionDate">{{ $d(Date.parse(workOrdersHistory.actionDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('maintenanceApp.workOrdersHistory.note')">Note</span>
          </dt>
          <dd>
            <span>{{ workOrdersHistory.note }}</span>
          </dd>
          <dt>
            <span v-text="$t('maintenanceApp.workOrdersHistory.status')">Status</span>
          </dt>
          <dd>
            <div v-if="workOrdersHistory.status">
              <router-link :to="{ name: 'StatusView', params: { statusId: workOrdersHistory.status.id } }">{{
                workOrdersHistory.status.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('maintenanceApp.workOrdersHistory.action')">Action</span>
          </dt>
          <dd>
            <div v-if="workOrdersHistory.action">
              <router-link :to="{ name: 'ActionsView', params: { actionsId: workOrdersHistory.action.id } }">{{
                workOrdersHistory.action.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('maintenanceApp.workOrdersHistory.workOrders')">Work Orders</span>
          </dt>
          <dd>
            <div v-if="workOrdersHistory.workOrders">
              <router-link :to="{ name: 'WorkOrdersView', params: { workOrdersId: workOrdersHistory.workOrders.id } }">{{
                workOrdersHistory.workOrders.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="workOrdersHistory.id"
          :to="{ name: 'WorkOrdersHistoryEdit', params: { workOrdersHistoryId: workOrdersHistory.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./work-orders-history-details.component.ts"></script>
