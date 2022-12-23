<template>
  <div>
    <b-dropdown-item to="/region">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.region')">Region</span>
    </b-dropdown-item>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.country')">Country</span>
    </b-dropdown-item>
    <b-dropdown-item to="/location">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.location')">Location</span>
    </b-dropdown-item>
    <b-dropdown-item to="/department">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.department')">Department</span>
    </b-dropdown-item>
    <b-dropdown-item to="/task">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.task')">Task</span>
    </b-dropdown-item>
    <b-dropdown-item to="/employee">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.employee')">Employee</span>
    </b-dropdown-item>
    <b-dropdown-item to="/job">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.job')">Job</span>
    </b-dropdown-item>
    <b-dropdown-item to="/job-history">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.jobHistory')">Job History</span>
    </b-dropdown-item>
    <b-dropdown-item to="/client">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.client')">Client</span>
    </b-dropdown-item>
    <b-dropdown-item to="/appintment">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.appintment')">Appintment</span>
    </b-dropdown-item>
    <b-dropdown-item to="/actions">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.actions')">Actions</span>
    </b-dropdown-item>
    <b-dropdown-item to="/item-models">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.itemModels')">Item Models</span>
    </b-dropdown-item>
    <b-dropdown-item to="/item-brand">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.itemBrand')">Item Brand</span>
    </b-dropdown-item>
    <b-dropdown-item to="/status">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.status')">Status</span>
    </b-dropdown-item>
    <b-dropdown-item to="/work-orders">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.workOrders')">Work Orders</span>
    </b-dropdown-item>
    <b-dropdown-item to="/work-orders-history">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.workOrdersHistory')">Work Orders History</span>
    </b-dropdown-item>
    <b-dropdown-item to="/attachments">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.attachments')">Attachments</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
