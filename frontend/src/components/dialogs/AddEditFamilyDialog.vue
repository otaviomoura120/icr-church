<script setup lang="ts">
import { ref, watch } from 'vue'
import type { Family, CreateFamilyPayload, FamilyAddress } from '@/composables/useFamily'
import { useFamily } from '@/composables/useFamily'

const props = defineProps<{
  isDialogVisible: boolean
  family: Family | null
}>()

const emit = defineEmits<{
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'saved'): void
}>()

const { createFamily, updateFamily } = useFamily()

const form = ref<{ validate: () => Promise<{ valid: boolean }> } | null>(null)
const isLoading = ref(false)
const errorMessage = ref<string | null>(null)

const defaultAddress = (): FamilyAddress => ({
  street: '',
  zipCode: '',
  country: '',
  state: '',
  city: '',
  neighborhood: '',
})

const name = ref('')
const address = ref<FamilyAddress>(defaultAddress())

watch(
  () => props.isDialogVisible,
  visible => {
    if (visible) {
      errorMessage.value = null
      if (props.family) {
        name.value = props.family.name
        address.value = { ...props.family.address }
      }
      else {
        name.value = ''
        address.value = defaultAddress()
      }
    }
  },
)

const required = (v: string) => !!v?.trim() || 'This field is required'

async function onSubmit() {
  const { valid } = await form.value!.validate()
  if (!valid) return

  isLoading.value = true
  errorMessage.value = null

  try {
    const payload: CreateFamilyPayload = { name: name.value.trim(), address: { ...address.value } }
    if (props.family) {
      await updateFamily(props.family.id, { version: props.family.version, ...payload })
    }
    else {
      await createFamily(payload)
    }
    emit('saved')
    emit('update:isDialogVisible', false)
  }
  catch (err: unknown) {
    const msg = err instanceof Error ? err.message : 'An unexpected error occurred'
    errorMessage.value = msg
  }
  finally {
    isLoading.value = false
  }
}

function onClose() {
  emit('update:isDialogVisible', false)
}
</script>

<template>
  <VDialog
    :model-value="isDialogVisible"
    max-width="700"
    @update:model-value="emit('update:isDialogVisible', $event)"
  >
    <VCard>
      <VCardTitle class="d-flex align-center justify-space-between pa-4 pb-2">
        <span>{{ family ? 'Edit Family' : 'New Family' }}</span>
        <DialogCloseBtn @click="onClose" />
      </VCardTitle>

      <VDivider />

      <VCardText class="pa-4">
        <VAlert
          v-if="errorMessage"
          type="error"
          variant="tonal"
          class="mb-4"
          closable
          @click:close="errorMessage = null"
        >
          {{ errorMessage }}
        </VAlert>

        <VForm ref="form" @submit.prevent="onSubmit">
          <VRow>
            <VCol cols="12">
              <AppTextField
                v-model="name"
                label="Family Name"
                :rules="[required]"
                placeholder="Enter family name"
              />
            </VCol>

            <VCol cols="12">
              <p class="text-body-1 font-weight-medium mb-3">
                Address
              </p>
            </VCol>

            <VCol cols="12" sm="6">
              <AppTextField
                v-model="address.street"
                label="Street"
                :rules="[required]"
                placeholder="Street address"
              />
            </VCol>

            <VCol cols="12" sm="6">
              <AppTextField
                v-model="address.zipCode"
                label="Zip Code"
                :rules="[required]"
                placeholder="ZIP / Postal code"
              />
            </VCol>

            <VCol cols="12" sm="6">
              <AppTextField
                v-model="address.city"
                label="City"
                :rules="[required]"
                placeholder="City"
              />
            </VCol>

            <VCol cols="12" sm="6">
              <AppTextField
                v-model="address.state"
                label="State"
                :rules="[required]"
                placeholder="State / Province"
              />
            </VCol>

            <VCol cols="12" sm="6">
              <AppTextField
                v-model="address.neighborhood"
                label="Neighborhood"
                :rules="[required]"
                placeholder="Neighborhood / District"
              />
            </VCol>

            <VCol cols="12" sm="6">
              <AppTextField
                v-model="address.country"
                label="Country"
                :rules="[required]"
                placeholder="Country"
              />
            </VCol>
          </VRow>
        </VForm>
      </VCardText>

      <VDivider />

      <VCardActions class="pa-4">
        <VSpacer />
        <VBtn
          variant="tonal"
          :disabled="isLoading"
          @click="onClose"
        >
          Cancel
        </VBtn>
        <VBtn
          color="primary"
          :loading="isLoading"
          @click="onSubmit"
        >
          Save
        </VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</template>
