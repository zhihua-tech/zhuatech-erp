/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import { defineStore } from 'pinia'
import { api } from '../api/erp'

export const useAuthStore = defineStore('auth', {
  state: () => ({ user: JSON.parse(localStorage.getItem('zhuatech_erp_user') || 'null') }),
  actions: {
    async login(form) {
      const result = await api.login(form)
      localStorage.setItem('zhuatech_erp_token', result.token)
      localStorage.setItem('zhuatech_erp_user', JSON.stringify(result.user))
      this.user = result.user
    },
    logout() {
      localStorage.removeItem('zhuatech_erp_token')
      localStorage.removeItem('zhuatech_erp_user')
      this.user = null
    }
  }
})

