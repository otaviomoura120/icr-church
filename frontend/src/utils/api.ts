import { ofetch } from 'ofetch'

function snakeToCamel(s: string) {
  return s.replace(/_([a-z])/g, (_, c) => c.toUpperCase())
}

function camelToSnake(s: string) {
  return s.replace(/([A-Z])/g, '_$1').toLowerCase()
}

function transformKeys(obj: unknown, convert: (s: string) => string): unknown {
  if (Array.isArray(obj)) return obj.map(v => transformKeys(v, convert))
  if (obj !== null && typeof obj === 'object')
    return Object.fromEntries(
      Object.entries(obj as object).map(([k, v]) => [convert(k), transformKeys(v, convert)])
    )
  return obj
}

export const $api = ofetch.create({
  // baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  baseURL: 'http://localhost:8080',
  async onRequest({ options }) {
    const accessToken = useCookie('accessToken').value

    if (!options.headers)
      options.headers = new Headers()
    else if (!(options.headers instanceof Headers))
      options.headers = new Headers(options.headers as HeadersInit)

    if (accessToken)
      options.headers.append('Authorization', `Bearer ${accessToken}`)
    if (options.body && typeof options.body === 'object')
      options.body = transformKeys(options.body, camelToSnake) as BodyInit
  },
  async onResponse({ response }) {
    if (response._data && typeof response._data === 'object')
      response._data = transformKeys(response._data, snakeToCamel)
  },
})
