<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="maintenanceApp.actions.home.createOrEditLabel"
          data-cy="ActionsCreateUpdateHeading"
          v-text="$t('maintenanceApp.actions.home.createOrEditLabel')"
        >
          Create or edit a Actions
        </h2>
        <div>
          <div class="form-group" v-if="actions.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="actions.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('maintenanceApp.actions.actionName')" for="actions-actionName">Action Name</label>
            <input
              type="text"
              class="form-control"
              name="actionName"
              id="actions-actionName"
              data-cy="actionName"
              :class="{ valid: !$v.actions.actionName.$invalid, invalid: $v.actions.actionName.$invalid }"
              v-model="$v.actions.actionName.$model"
            />
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
            :disabled="$v.actions.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./actions-update.component.ts"></script>
