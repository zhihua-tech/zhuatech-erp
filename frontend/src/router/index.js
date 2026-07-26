/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainLayout from '../components/MainLayout.vue'

const routes = [
  { path: '/login', component: LoginView, meta: { public: true, title: '登录' } },
  { path: '/', component: MainLayout, children: [
    { path: '', component: () => import('../views/HomeView.vue'), meta: { title: '经营驾驶舱' } },
    { path: 'workbench', component: () => import('../views/WorkbenchView.vue'), meta: { title: '业务工作台' } },
    { path: 'sales', component: () => import('../views/SalesView.vue'), meta: { title: '销售订单' } },
    { path: 'purchase', component: () => import('../views/PurchaseView.vue'), meta: { title: '采购订单' } },
    { path: 'inventory', component: () => import('../views/InventoryView.vue'), meta: { title: '库存管理' } },
    { path: 'finance', component: () => import('../views/FinanceView.vue'), meta: { title: '财务往来' } },
    { path: 'partners', component: () => import('../views/PartnersView.vue'), meta: { title: '客户与供应商' } },
    { path: 'products', component: () => import('../views/ProductsView.vue'), meta: { title: '商品资料' } },
    { path: 'profile', component: () => import('../views/ProfileView.vue'), meta: { title: '个人中心' } }
  ] }
]

const router = createRouter({ history: createWebHistory(), routes, scrollBehavior: () => ({ top: 0 }) })
router.beforeEach(to => {
  document.title = `${to.meta.title || '企业资源管理'}｜知华科技 ERP`
  if (!to.meta.public && !localStorage.getItem('zhuatech_erp_token')) return '/login'
  if (to.path === '/login' && localStorage.getItem('zhuatech_erp_token')) return '/'
})

export default router

