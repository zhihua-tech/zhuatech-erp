<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { onMounted, ref } from 'vue'
import { api } from '../api/erp'

const products = ref([])
onMounted(async () => { products.value = await api.products() })
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">商品资料</h1><div class="page-subtitle">SKU、价格与库存口径</div></div><span class="head-action"><van-icon name="plus" size="20" /></span></div>
    <van-search placeholder="搜索商品名称或 SKU" shape="round" background="transparent" />
    <section v-for="product in products" :key="product.id" class="card product-card">
      <span class="product-icon"><van-icon :name="product.category==='智能硬件'?'desktop-o':product.category==='专业服务'?'service-o':'apps-o'" /></span>
      <div class="product-main"><div class="list-title">{{ product.name }}</div><div class="list-code">{{ product.sku }} · {{ product.category }}</div><div class="price-row"><span>成本 <b>¥{{ product.costPrice }}</b></span><span>售价 <b>¥{{ product.salePrice }}</b></span><span>库存 <b>{{ product.stockOnHand }} {{ product.unit }}</b></span></div></div>
    </section>
  </div>
</template>

<style scoped>
.van-search{margin:-8px -12px 12px}.product-card{display:flex;gap:13px;padding:15px}.product-icon{flex:none;width:45px;height:45px;display:grid;place-items:center;border-radius:15px;background:#e8f5f3;color:#0c958a;font-size:21px}.product-main{flex:1;min-width:0}.price-row{display:flex;justify-content:space-between;margin-top:13px;padding-top:11px;border-top:1px solid var(--line);color:#8a9ca3;font-size:9px}.price-row b{display:block;margin-top:3px;color:#425a65;font-size:11px}
</style>

