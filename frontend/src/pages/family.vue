<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import type { Family } from '@/composables/useFamily'
import { useFamily } from '@/composables/useFamily'
import AddEditFamilyDialog from '@/components/dialogs/AddEditFamilyDialog.vue'

const { fetchFamilies, deleteFamily } = useFamily()

const families = ref<Family[]>([])
const totalElements = ref(0)
const isLoading = ref(false)
const errorMessage = ref<string | null>(null)

const page = ref(1) // 1-based for VPagination/TablePagination
const itemsPerPage = ref(20)
const search = ref('')

const isDialogVisible = ref(false)
const selectedFamily = ref<Family | null>(null)

const isDeleteDialogVisible = ref(false)
const familyToDelete = ref<Family | null>(null)
const isDeleting = ref(false)

async function fetchList() {
  isLoading.value = true
  try {
    const result = await fetchFamilies({
      search: search.value || undefined,
      page: page.value - 1, // convert 1-based UI page to 0-based API page
      size: itemsPerPage.value,
      sortBy: 'name',
      sortDirection: 'ASC',
    })
    families.value = result.content
    totalElements.value = result.totalElements
  }
  catch (error) {
    console.error('Error fetching families:', error)
    families.value = []
    totalElements.value = 0
  }
  finally {
    isLoading.value = false
  }
}

function openCreateDialog() {
  selectedFamily.value = null
  isDialogVisible.value = true
}

function openEditDialog(family: Family) {
  selectedFamily.value = family
  isDialogVisible.value = true
}

function openDeleteDialog(family: Family) {
  familyToDelete.value = family
  isDeleteDialogVisible.value = true
}

async function confirmDelete() {
  if (!familyToDelete.value) return
  isDeleting.value = true
  try {
    await deleteFamily(familyToDelete.value.id)
    isDeleteDialogVisible.value = false
    familyToDelete.value = null
    await fetchList()
  } catch (error) {
    errorMessage.value = error.response._data
    isDeleteDialogVisible.value = false
  }
  finally {
    isDeleting.value = false
  }
}

function formatDate(iso: string) {
  return new Date(iso).toLocaleDateString()
}

let searchTimeout: ReturnType<typeof setTimeout> | null = null
watch(search, () => {
  if (searchTimeout) clearTimeout(searchTimeout)
  searchTimeout = setTimeout(() => {
    page.value = 1
    fetchList()
  }, 400)
})

watch([page, itemsPerPage], fetchList)

onMounted(fetchList)
</script>

<template>
  <VCard>
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
    <VCardText>
      <!-- Top bar -->
      <div class="d-flex align-center gap-4 flex-wrap mb-4">
        <VTextField
          v-model="search"
          placeholder="Search families..."
          prepend-inner-icon="tabler-search"
          clearable
          hide-details
          density="compact"
          style="max-inline-size: 300px"
        />
        <VSpacer />
        <VBtn
          color="primary"
          prepend-icon="tabler-plus"
          @click="openCreateDialog"
        >
          New Family
        </VBtn>
      </div>

      <!-- Table -->
      <VTable>
        <thead>
          <tr>
            <th>Name</th>
            <th>City</th>
            <th>State</th>
            <th>Created</th>
            <th class="text-center">
              Actions
            </th>
          </tr>
        </thead>
        <tbody>
          <!-- Loading skeleton -->
          <template v-if="isLoading">
            <tr v-for="n in itemsPerPage" :key="n">
              <td colspan="5">
                <VSkeletonLoader type="text" />
              </td>
            </tr>
          </template>

          <!-- Data rows -->
          <template v-else-if="families.length > 0">
            <tr v-for="family in families" :key="family.id">
              <td>{{ family.name }}</td>
              <td>{{ family.address.city }}</td>
              <td>{{ family.address.state }}</td>
              <td>{{ formatDate(family.createdAt) }}</td>
              <td class="text-center">
                <VBtn
                  icon
                  variant="text"
                  size="small"
                  @click="openEditDialog(family)"
                >
                  <VIcon icon="tabler-edit" />
                </VBtn>
                <VBtn
                  icon
                  variant="text"
                  size="small"
                  color="error"
                  @click="openDeleteDialog(family)"
                >
                  <VIcon icon="tabler-trash" />
                </VBtn>
              </td>
            </tr>
          </template>

          <!-- Empty state -->
          <tr v-else>
            <td colspan="5" class="text-center pa-8 text-medium-emphasis">
              No families found
            </td>
          </tr>
        </tbody>
      </VTable>

      <!-- Pagination -->
      <TablePagination
        v-model:page="page"
        :items-per-page="itemsPerPage"
        :total-items="totalElements"
        class="mt-4"
      />
    </VCardText>
  </VCard>

  <!-- Add / Edit dialog -->
  <AddEditFamilyDialog
    v-model:is-dialog-visible="isDialogVisible"
    :family="selectedFamily"
    @saved="fetchList"
  />

  <!-- Delete confirmation dialog -->
  <VDialog
    v-model="isDeleteDialogVisible"
    max-width="400"
  >
    <VCard>
      <VCardTitle class="pa-4">
        Delete Family
      </VCardTitle>
      <VCardText class="pa-4 pt-0">
        Are you sure you want to delete <strong>{{ familyToDelete?.name }}</strong>? This action cannot be undone.
      </VCardText>
      <VCardActions class="pa-4">
        <VSpacer />
        <VBtn
          variant="tonal"
          v-model:disabled="isDeleting"
          @click="isDeleteDialogVisible = false"
        >
          Cancel
        </VBtn>
        <VBtn
          color="error"
          :loading="isDeleting"
          @click="confirmDelete"
        >
          Delete
        </VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</template>
