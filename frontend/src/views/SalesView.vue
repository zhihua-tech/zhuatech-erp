<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/erp'

const orders = ref([])
const active = ref('ALL')
const filters = [['ALL','全部'],['DRAFT','草稿'],['CONFIRMED','已确认'],['SHIPPED','已发货'],['COMPLETED','已完成']]
const labels = { DRAFT:'草稿', CONFIRMED:'已确认', SHIPPED:'已发货', COMPLETED:'已完成', CANCELLED:'已取消' }
const tones = { DRAFT:'gray', CONFIRMED:'orange', SHIPPED:'', COMPLETED:'', CANCELLED:'red' }
const visible = computed(() => active.value === 'ALL' ? orders.value : orders.value.filter(item => item.status === active.value))
const total = computed(() => orders.value.reduce((sum, item) => sum + item.totalAmount, 0))
const money = value => Number(value || 0).toLocaleString('zh-CN')

onMounted(async () => { orders.value = await api.salesOrders() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">销售订单</h1><div class="page-subtitle">从报价确认到发货回款</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <section class="sales-summary"><div><small>订单总额</small><strong>¥{{ money(total) }}</strong></div><div><small>待收金额</small><strong>¥{{ money(orders.reduce((s,o)=>s+o.unpaidAmount,0)) }}</strong></div></section>
    <div class="filter-strip"><button v-for="item in filters" :key="item[0]" class="filter-chip" :class="{active:active===item[0]}" @click="active=item[0]">{{ item[1] }}</button></div>
    <section v-for="order in visible" :key="order.id" class="card list-card">
      <div class="list-top"><div><div class="list-title">{{ order.customerName }}</div><div class="list-code">{{ order.orderNo }} · {{ order.orderDate }}</div></div><span class="tag" :class="tones[order.status]">{{ labels[order.status] }}</span></div>
      <div class="goods"><van-icon name="bag-o" /> {{ order.itemSummary }}</div>
      <div class="progress"><i :style="{width: Math.min(100,order.paidAmount/order.totalAmount*100)+'%'}"></i></div>
      <div class="amount-row"><div><span class="muted">订单金额</span><strong class="money">¥{{ money(order.totalAmount) }}</strong></div><div class="right"><span class="muted">待收 ¥{{ money(order.unpaidAmount) }}</span><small>负责人 {{ order.ownerName }}</small></div></div>
    </section>
  </div>
</template>

<style scoped>
.sales-summary{display:grid;grid-template-columns:1fr 1fr;gap:1px;margin-bottom:18px;overflow:hidden;border-radius:20px;background:#315166}.sales-summary div{display:flex;flex-direction:column;padding:18px;background:var(--navy);color:#fff}.sales-summary small{color:#9fb8c2;font-size:10px}.sales-summary strong{margin-top:7px;font-size:20px}.goods{margin-top:15px;padding:11px;border-radius:12px;background:#f4f8f8;color:#58717c;font-size:11px}.goods .van-icon{margin-right:5px;color:var(--teal)}.progress{height:5px;margin-top:14px;border-radius:999px;background:#edf2f3;overflow:hidden}.progress i{display:block;height:100%;border-radius:999px;background:linear-gradient(90deg,var(--teal),#4bd2c6)}.amount-row>div:first-child{display:flex;flex-direction:column}.amount-row .right{display:flex;flex-direction:column;align-items:flex-end;gap:5px}.amount-row small{font-size:10px;color:#9aabb1}
</style>

