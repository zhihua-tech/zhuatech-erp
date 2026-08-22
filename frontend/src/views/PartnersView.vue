<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/erp'

const partners = ref([])
const active = ref('CUSTOMER')
const visible = computed(() => partners.value.filter(item => item.type === active.value))
onMounted(async () => { partners.value = await api.partners() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">商业伙伴</h1><div class="page-subtitle">客户与供应商统一视图</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <div class="partner-tabs"><button :class="{active:active==='CUSTOMER'}" @click="active='CUSTOMER'">客户 {{ partners.filter(p=>p.type==='CUSTOMER').length }}</button><button :class="{active:active==='SUPPLIER'}" @click="active='SUPPLIER'">供应商 {{ partners.filter(p=>p.type==='SUPPLIER').length }}</button></div>
    <section v-for="partner in visible" :key="partner.id" class="card partner-card">
      <div class="avatar">{{ partner.name.slice(0,1) }}</div>
      <div class="partner-main"><div class="list-top"><div><div class="list-title">{{ partner.name }}</div><div class="list-code">{{ partner.code }} · {{ partner.address }}</div></div><span class="tag">正常合作</span></div><div class="contact"><span><van-icon name="contact-o" /> {{ partner.contactName }}</span><span><van-icon name="phone-o" /> {{ partner.phone }}</span></div><div v-if="partner.type==='CUSTOMER'" class="credit">信用额度 <b>¥{{ Number(partner.creditLimit).toLocaleString('zh-CN') }}</b></div></div>
    </section>
  </div>
</template>

<style scoped>
.partner-tabs{display:grid;grid-template-columns:1fr 1fr;gap:5px;margin-bottom:17px;padding:4px;border-radius:15px;background:#e6edef}.partner-tabs button{height:38px;border:0;border-radius:11px;background:transparent;color:#70848d}.partner-tabs button.active{background:#fff;color:var(--navy);font-weight:700;box-shadow:0 5px 14px rgba(20,49,63,.08)}.partner-card{display:flex;gap:13px;padding:15px}.avatar{flex:none;width:45px;height:45px;display:grid;place-items:center;border-radius:15px;background:linear-gradient(135deg,var(--navy-soft),#2b6a78);color:#66e0d5;font-size:18px;font-weight:800}.partner-main{flex:1;min-width:0}.contact{display:flex;gap:15px;margin-top:13px;color:#607680;font-size:10px}.contact .van-icon{color:var(--teal)}.credit{margin-top:12px;padding-top:10px;border-top:1px dashed var(--line);color:#8b9ca3;font-size:10px}.credit b{float:right;color:var(--navy);font-size:12px}
</style>

