<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/erp'

const records = ref([])
const labels = { RECEIVABLE:'应收款', PAYABLE:'应付款', EXPENSE:'费用', RECEIPT:'收款' }
const status = { PENDING:'待处理', PARTIAL:'部分核销', SETTLED:'已结清' }
const receivable = computed(() => records.value.filter(r=>r.type==='RECEIVABLE').reduce((s,r)=>s+r.outstandingAmount,0))
const payable = computed(() => records.value.filter(r=>r.type==='PAYABLE').reduce((s,r)=>s+r.outstandingAmount,0))
const money = value => Number(value || 0).toLocaleString('zh-CN')
onMounted(async () => { records.value = await api.financeRecords() })
</script>

<template>
  <div class="page safe-top finance-page">
    <div class="page-head"><div><h1 class="page-title">财务往来</h1><div class="page-subtitle">应收、应付、费用与核销</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <section class="balance-card">
      <div class="balance-head"><span>资金敞口</span><small>单位：人民币</small></div>
      <div class="balance-body"><div><small>待收</small><strong>¥{{ money(receivable) }}</strong></div><i></i><div><small>待付</small><strong>¥{{ money(payable) }}</strong></div></div>
      <div class="net">净流入预期 <b>¥{{ money(receivable-payable) }}</b><span>状态健康</span></div>
    </section>
    <div class="filter-strip"><button class="filter-chip active">全部</button><button class="filter-chip">应收</button><button class="filter-chip">应付</button><button class="filter-chip">费用</button></div>
    <section v-for="record in records" :key="record.id" class="card finance-row">
      <span class="finance-icon" :class="record.type.toLowerCase()"><van-icon :name="record.type==='RECEIVABLE'?'cash-back-record':record.type==='PAYABLE'?'peer-pay':'bill-o'" /></span>
      <div class="finance-main"><div class="list-top"><div><div class="list-title">{{ record.partnerName }}</div><div class="list-code">{{ record.recordNo }} · {{ labels[record.type] }}</div></div><span class="tag" :class="record.status==='SETTLED'?'':record.status==='PARTIAL'?'orange':'gray'">{{ status[record.status] }}</span></div>
        <div class="amount-row"><div><span class="muted">单据金额</span><strong>¥{{ money(record.amount) }}</strong></div><div class="due"><span>未结 ¥{{ money(record.outstandingAmount) }}</span><small>到期 {{ record.dueDate }}</small></div></div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.balance-card{margin-bottom:17px;padding:20px;border-radius:23px;color:#fff;background:linear-gradient(135deg,#0a293b,#174c5d)}.balance-head{display:flex;justify-content:space-between;color:#b4cad1;font-size:11px}.balance-head small{font-size:9px}.balance-body{display:grid;grid-template-columns:1fr 1px 1fr;gap:18px;margin:21px 0}.balance-body div{display:flex;flex-direction:column}.balance-body small{color:#99b5bf;font-size:10px}.balance-body strong{font-size:23px;margin-top:7px}.balance-body i{background:#426572}.net{display:flex;align-items:center;gap:6px;padding-top:13px;border-top:1px solid #345868;color:#9fb8c1;font-size:10px}.net b{color:#62ddd2}.net span{margin-left:auto;padding:4px 8px;border-radius:99px;color:#7be7de;background:rgba(62,203,190,.13)}.finance-row{display:flex;gap:12px;padding:15px}.finance-icon{flex:none;width:42px;height:42px;display:grid;place-items:center;border-radius:14px;background:#e5f6f3;color:#0b9388;font-size:20px}.finance-icon.payable{background:#fff0df;color:#d9721c}.finance-icon.expense{background:#ffeded;color:#d35850}.finance-main{flex:1;min-width:0}.amount-row>div:first-child{display:flex;flex-direction:column}.due{display:flex;flex-direction:column;align-items:flex-end;gap:5px;color:#647982;font-size:10px}.due small{color:#9aa9af;font-size:9px}
</style>

