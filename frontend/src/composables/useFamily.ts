import { $api } from '@/utils/api'

export interface FamilyAddress {
  street: string
  zipCode: string
  country: string
  state: string
  city: string
  neighborhood: string
}

export interface Family {
  id: number
  name: string
  version: number
  address: FamilyAddress
  createdAt: string
  updatedAt: string
}

export interface FamilyPage {
  content: Family[]
  totalElements: number
  totalPages: number
  page: number
  size: number
}

export interface CreateFamilyPayload {
  name: string
  address: FamilyAddress
}

export interface UpdateFamilyPayload {
  version: number
  name: string
  address: FamilyAddress
}

export interface FetchFamiliesParams {
  search?: string
  sortBy?: string
  sortDirection?: string
  page?: number
  size?: number
}

export function useFamily() {
  function fetchFamilies(params: FetchFamiliesParams = {}): Promise<FamilyPage> {
    const query: Record<string, string | number> = {}
    if (params.search) query.search = params.search
    if (params.sortBy) query.sortBy = params.sortBy
    if (params.sortDirection) query.sortDirection = params.sortDirection
    if (params.page !== undefined) query.page = params.page
    if (params.size !== undefined) query.size = params.size

    return $api<FamilyPage>('/family', { method: 'GET', query: query })
  }

  function createFamily(payload: CreateFamilyPayload): Promise<Family> {
    return $api<Family>('/family', { method: 'POST', body: payload })
  }

  function updateFamily(id: number, payload: UpdateFamilyPayload): Promise<Family> {
    return $api<Family>(`/family/${id}`, { method: 'PUT', body: payload })
  }

  function deleteFamily(id: number): Promise<void> {
    return $api<void>(`/family/${id}`, { method: 'DELETE', headers: {
        'Content-Type': 'application/json'
      } })
  }

  return { fetchFamilies, createFamily, updateFamily, deleteFamily }
}
