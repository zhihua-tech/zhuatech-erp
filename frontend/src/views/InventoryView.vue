<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. -->
<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/erp'

const products = ref([])
const movements = ref([])
const planning = ref(null)
const planningLoading = ref(false)
const inventoryValue = computed(() => products.value.reduce((sum, item) => sum + item.costPrice * item.stockOnHand, 0))
const money = value => Number(value || 0).toLocaleString('zh-CN', { maximumFractionDigits: 0 })
const typeLabel = { INBOUND:'入库', OUTBOUND:'出库', ADJUSTMENT:'盘点调整' }
const runMaterialPlan = async () => {
  planningLoading.value = true
  try {
    planning.value = await api.materialPlan({ items: products.value.map(product => ({
      sku: product.sku, name: product.name, onHand: product.stockOnHand,
      safetyStock: product.safetyStock, forecastDemand: product.lowStock ? 60 : Math.ceil(product.stockOnHand * .35),
      inboundQty: product.lowStock ? 20 : 0, leadDays: product.lowStock ? 18 : 7, unitCost: product.costPrice
    })) })
  } finally { planningLoading.value = false }
}
onMounted(async () => { [products.value, movements.value] = await Promise.all([api.products(), api.stockMovements()]) })
</script>

<template>
  <div class="page safe-top inventory-page">
    <div class="page-head"><div><h1 class="page-title">库存管理</h1><div class="page-subtitle">实时库存与出入库追踪</div></div><span class="head-action"><van-icon name="scan" size="20" /></span></div>
    <section class="inventory-hero">
      <div><small>当前库存成本</small><strong>¥{{ money(inventoryValue) }}</strong><span>{{ products.length }} 个 SKU · 数据刚刚更新</span></div>
      <div class="ring"><b>{{ products.filter(p=>p.lowStock).length }}</b><span>预警</span></div>
    </section>
    <div class="quick-actions"><button><van-icon name="down" />采购入库</button><button><van-icon name="upgrade" />销售出库</button><button><van-icon name="records-o" />库存盘点</button></div>
    <section class="card planning-card">
      <div class="planning-head"><div><small>需求预测 · 补货建议</small><h2>物料短缺计划</h2></div><button :disabled="planningLoading" @click="runMaterialPlan">{{ planningLoading ? '测算中' : '生成计划' }}</button></div>
      <p v-if="!planning">结合现存量、安全库存、在途和供应周期，定位未来短缺并估算补货资金。</p>
      <template v-else>
        <div class="planning-kpis"><span><b>{{ planning.shortageItems }}</b>项风险物料</span><span><b>¥{{ money(planning.capitalRequired) }}</b>建议资金</span></div>
        <div v-for="item in planning.items.filter(item => item.riskLevel !== 'LOW')" :key="item.sku" class="planning-row">
          <div><b>{{ item.name }}</b><small>{{ item.sku }} · 预计库存 {{ item.projectedStock }}</small></div>
          <span>建议补 {{ item.suggestedOrder }}</span>
        </div>
        <p class="planning-note">{{ planning.recommendation }}</p>
      </template>
    </section>
    <h2 class="section-title">库存状态 <small>按 SKU 查看</small></h2>
    <section v-for="product in products" :key="product.id" class="card stock-card" :class="{warning:product.lowStock}">
      <div class="stock-icon"><van-icon :name="product.category==='智能硬件'?'desktop-o':'apps-o'" /></div>
      <div class="stock-main"><div class="list-top"><div><div class="list-title">{{ product.name }}</div><div class="list-code">{{ product.sku }} · {{ product.category }}</div></div><span v-if="product.lowStock" class="tag red">低库存</span></div>
        <div class="stock-data"><b>{{ product.stockOnHand }}</b><span>{{ product.unit }}</span><i></i><small>安全库存 {{ product.safetyStock }}</small></div>
        <div class="stock-bar"><i :style="{width:Math.min(100, product.stockOnHand/Math.max(product.safetyStock,1)*45)+'%'}"></i></div>
      </div>
    </section>
    <h2 class="section-title">最新库存流水 <small>{{ movements.length }} 条</small></h2>
    <section class="card movement-list">
      <div v-for="movement in movements" :key="movement.id" class="movement-row">
        <span class="move-icon" :class="movement.type.toLowerCase()"><van-icon :name="movement.type==='INBOUND'?'down':movement.type==='OUTBOUND'?'upgrade':'replay'" /></span>
        <div><b>{{ movement.productName }}</b><small>{{ movement.referenceNo }} · {{ movement.handledBy }}</small></div>
        <strong :class="movement.type==='OUTBOUND'?'minus':'plus'">{{ movement.type==='OUTBOUND'?'-':'+' }}{{ movement.quantity }}</strong>
      </div>
    </section>
  </div>
</template>

<style scoped>
.planning-card{padding:16px;margin-bottom:17px}.planning-head{display:flex;align-items:center;justify-content:space-between}.planning-head small{color:#72878f;font-size:9px}.planning-head h2{margin:3px 0 0;font-size:15px}.planning-head button{padding:9px 13px;border:0;border-radius:10px;background:#0b756f;color:white;font-size:10px}.planning-head button:disabled{opacity:.6}.planning-card>p{margin:11px 0 0;color:#74888f;font-size:10px;line-height:1.7}.planning-kpis{display:flex;gap:18px;margin:14px 0 7px;padding:11px;border-radius:12px;background:#f2f7f7}.planning-kpis span{display:flex;flex-direction:column;color:#788b91;font-size:9px}.planning-kpis b{margin-bottom:3px;color:#15353d;font-size:15px}.planning-row{display:flex;align-items:center;justify-content:space-between;padding:9px 1px;border-bottom:1px solid #edf2f3}.planning-row>div{display:flex;flex-direction:column;gap:3px}.planning-row b{font-size:11px}.planning-row small{color:#8b9ca3;font-size:8px}.planning-row>span{color:#cf681f;font-size:10px}.planning-note{color:#49676d!important}
.inventory-hero{display:flex;align-items:center;justify-content:space-between;padding:21px;border-radius:23px;color:#fff;background:linear-gradient(135deg,#0b2538,#185164)}.inventory-hero>div:first-child{display:flex;flex-direction:column}.inventory-hero small{color:#a8c2cb;font-size:10px}.inventory-hero strong{font-size:27px;margin:7px 0 5px}.inventory-hero span{color:#8fb0bc;font-size:9px}.ring{width:65px;height:65px;display:grid;place-content:center;text-align:center;border-radius:50%;border:6px solid #2b6d76;border-top-color:#f6a353}.ring b{font-size:19px}.ring span{font-size:9px}.quick-actions{display:grid;grid-template-columns:repeat(3,1fr);gap:9px;margin:12px 0}.quick-actions button{height:43px;border:1px solid var(--line);border-radius:13px;background:#fff;color:#49636f;font-size:11px}.quick-actions .van-icon{margin-right:4px;color:var(--teal)}.stock-card{display:flex;gap:13px;padding:15px}.stock-card.warning{border-color:#ffd8ce}.stock-icon{flex:none;width:42px;height:42px;display:grid;place-items:center;border-radius:14px;background:#e9f5f4;color:var(--teal);font-size:20px}.warning .stock-icon{background:#fff0ed;color:#d7594f}.stock-main{flex:1;min-width:0}.stock-data{display:flex;align-items:baseline;gap:4px;margin-top:11px}.stock-data b{font-size:21px}.stock-data span,.stock-data small{font-size:10px;color:var(--muted)}.stock-data i{width:1px;height:12px;margin:0 7px;background:#d9e2e4}.stock-bar{height:4px;margin-top:9px;overflow:hidden;border-radius:99px;background:#edf2f3}.stock-bar i{display:block;height:100%;background:var(--teal)}.warning .stock-bar i{background:#e76a5e}.movement-list{padding:6px 15px}.movement-row{display:flex;align-items:center;gap:11px;padding:13px 0;border-bottom:1px solid #edf2f3}.movement-row:last-child{border-bottom:0}.move-icon{width:34px;height:34px;display:grid;place-items:center;border-radius:11px;background:#e5f6f3;color:#0a8f84}.move-icon.outbound{background:#fff0df;color:#d6721e}.move-icon.adjustment{background:#eaf1f7;color:#4678a0}.movement-row>div{display:flex;flex:1;min-width:0;flex-direction:column;gap:4px}.movement-row b{font-size:12px}.movement-row small{overflow:hidden;text-overflow:ellipsis;white-space:nowrap;color:#8b9ca3;font-size:9px}.movement-row>strong{font-size:13px}.plus{color:#0e968a}.minus{color:#d97220}
</style>
