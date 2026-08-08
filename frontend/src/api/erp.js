/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import http from './http'
import { mockApi } from './mock'

const realApi = {
  login: data => http.post('/auth/login', data),
  me: () => http.get('/auth/me'),
  dashboard: () => http.get('/erp/dashboard'),
  products: () => http.get('/erp/products'),
  partners: type => http.get('/erp/partners', { params: type ? { type } : {} }),
  salesOrders: () => http.get('/erp/sales-orders'),
  purchaseOrders: () => http.get('/erp/purchase-orders'),
  stockMovements: () => http.get('/erp/stock-movements'),
  financeRecords: () => http.get('/erp/finance-records'),
  materialPlan: data => http.post('/erp/insights/material-shortage', data)
}

export const api = import.meta.env.VITE_DEMO_MODE === 'true' ? mockApi : realApi
