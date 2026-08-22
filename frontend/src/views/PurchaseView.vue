<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { onMounted, ref } from 'vue'
import { api } from '../api/erp'

const orders = ref([])
const labels = { DRAFT:'待审批', APPROVED:'待到货', RECEIVED:'已入库', CANCELLED:'已取消' }
const tones = { DRAFT:'orange', APPROVED:'', RECEIVED:'', CANCELLED:'red' }
const money = value => Number(value || 0).toLocaleString('zh-CN')
onMounted(async () => { orders.value = await api.purchaseOrders() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">采购协同</h1><div class="page-subtitle">需求、审批、到货与入库</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <section class="summary-band"><div class="summary-cell"><span class="muted">待处理采购</span><strong>{{ orders.length }}</strong></div><div class="summary-cell"><span class="muted">采购总额</span><strong class="money">¥{{ money(orders.reduce((s,o)=>s+o.totalAmount,0)) }}</strong></div></section>
    <section class="card arrival"><div><span class="tag orange">最近到货</span><b>07 月 31 日</b><small>温湿度传感器 · 200 只</small></div><van-icon name="underway-o" size="34" /></section>
    <h2 class="section-title">采购订单 <small>按交期排序</small></h2>
    <section v-for="order in orders" :key="order.id" class="card list-card">
      <div class="list-top"><div><div class="list-title">{{ order.supplierName }}</div><div class="list-code">{{ order.orderNo }}</div></div><span class="tag" :class="tones[order.status]">{{ labels[order.status] }}</span></div>
      <div class="meta-row"><span><van-icon name="clock-o" /> 下单 {{ order.orderDate }}</span><span><van-icon name="send-gift-o" /> 预计 {{ order.expectedDate }}</span></div>
      <div class="purchase-goods">{{ order.itemSummary }}</div>
      <div class="amount-row"><strong class="money">¥{{ money(order.totalAmount) }}</strong><span class="muted">采购人 {{ order.buyerName }}</span></div>
    </section>
  </div>
</template>

<style scoped>
.summary-cell strong{font-size:18px}.arrival{display:flex;justify-content:space-between;align-items:center;color:#fff;background:linear-gradient(135deg,#167f78,#22b7aa)}.arrival>div{display:flex;flex-direction:column;gap:7px}.arrival .tag{align-self:flex-start;background:rgba(255,255,255,.17);color:#fff}.arrival small{color:#cef3ef}.purchase-goods{margin-top:14px;padding:12px;border-left:3px solid var(--orange);border-radius:0 12px 12px 0;background:#fff7ed;color:#725033;font-size:11px}
</style>

