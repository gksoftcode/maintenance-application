<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="maintenanceApp.itemBrand.home.createOrEditLabel"
          data-cy="ItemBrandCreateUpdateHeading"
          v-text="$t('maintenanceApp.itemBrand.home.createOrEditLabel')"
        >
          Create or edit a ItemBrand
        </h2>
        <div>
          <div class="form-group" v-if="itemBrand.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="itemBrand.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('maintenanceApp.itemBrand.brandName')" for="item-brand-brandName"
              >Brand Name</label
            >
            <input
              type="text"
              class="form-control"
              name="brandName"
              id="item-brand-brandName"
              data-cy="brandName"
              :class="{ valid: !$v.itemBrand.brandName.$invalid, invalid: $v.itemBrand.brandName.$invalid }"
              v-model="$v.itemBrand.brandName.$model"
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
            :disabled="$v.itemBrand.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./item-brand-update.component.ts"></script>
