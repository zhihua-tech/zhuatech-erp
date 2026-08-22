<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { onMounted, ref } from 'vue'
import { api } from '../api/erp'
import { useAuthStore } from '../stores/auth'

const auth = useAuthStore()
const dashboard = ref({ salesAmount: 0, activeSalesOrders: 0, pendingPurchaseOrders: 0, lowStockProducts: 0, outstandingReceivable: 0, outstandingPayable: 0, todayStockMovements: 0 })
const bars = [42, 58, 50, 72, 63, 86, 78]
const modules = [
  ['销售订单', 'orders-o', '/sales', 'navy'], ['采购协同', 'cart-o', '/purchase', 'orange'],
  ['库存管理', 'logistics', '/inventory', ''], ['财务往来', 'balance-list-o', '/finance', 'blue'],
  ['客户供应商', 'friends-o', '/partners', ''], ['商品资料', 'apps-o', '/products', 'orange'],
  ['经营报表', 'bar-chart-o', '', 'blue'], ['审批中心', 'passed', '', 'navy']
]
const money = value => Number(value || 0).toLocaleString('zh-CN', { minimumFractionDigits: 0 })

onMounted(async () => { dashboard.value = await api.dashboard() })
</script>

<template>
  <div class="page safe-top home-page">
    <header class="topbar">
      <div class="identity"><span class="mark">ZH</span><div><b>知华 ERP</b><small>{{ auth.user?.department }}</small></div></div>
      <div class="online"><i></i>数据在线</div>
    </header>

    <section class="hero-card">
      <div class="hero-top"><span>本期销售总额</span><span class="trend">↗ 12.8%</span></div>
      <div class="hero-money"><small>¥</small>{{ money(dashboard.salesAmount) }}</div>
      <div class="hero-foot"><span>你好，{{ auth.user?.fullName }}</span><span>2026 年 7 月经营期</span></div>
      <div class="orb orb-one"></div><div class="orb orb-two"></div>
    </section>

    <section class="kpi-grid">
      <div class="kpi"><span class="kpi-icon teal"><van-icon name="orders-o" /></span><div><strong>{{ dashboard.activeSalesOrders }}</strong><small>执行中销售</small></div></div>
      <div class="kpi"><span class="kpi-icon orange"><van-icon name="cart-o" /></span><div><strong>{{ dashboard.pendingPurchaseOrders }}</strong><small>待处理采购</small></div></div>
      <div class="kpi"><span class="kpi-icon red"><van-icon name="warning-o" /></span><div><strong>{{ dashboard.lowStockProducts }}</strong><small>库存预警</small></div></div>
      <div class="kpi"><span class="kpi-icon blue"><van-icon name="exchange" /></span><div><strong>{{ dashboard.todayStockMovements }}</strong><small>今日出入库</small></div></div>
    </section>

    <h2 class="section-title">业务入口 <small>经营流程一站贯通</small></h2>
    <section class="card module-grid">
      <component :is="item[2] ? 'router-link' : 'div'" v-for="item in modules" :key="item[0]" :to="item[2]" class="module-item" :class="{ disabled: !item[2] }">
        <span class="module-icon" :class="item[3]"><van-icon :name="item[1]" /></span><span>{{ item[0] }}</span>
      </component>
    </section>

    <h2 class="section-title">资金全景 <small>应收 / 应付</small></h2>
    <section class="cash-card">
      <div><span>待收款</span><strong class="money">¥{{ money(dashboard.outstandingReceivable) }}</strong><small>较上周下降 8.2%</small></div>
      <div class="divider"></div>
      <div><span>待付款</span><strong class="money">¥{{ money(dashboard.outstandingPayable) }}</strong><small>未来 30 天到期</small></div>
    </section>

    <h2 class="section-title">近 7 日业务热度 <small>订单与库存操作</small></h2>
    <section class="card chart-card">
      <div class="bars"><i v-for="(height, index) in bars" :key="index" :style="{ height: height + '%' }"><span></span></i></div>
      <div class="axis"><span>一</span><span>二</span><span>三</span><span>四</span><span>五</span><span>六</span><span>日</span></div>
    </section>

    <section v-if="dashboard.lowStockProducts" class="alert-card">
      <span class="alert-icon"><van-icon name="bulb-o" /></span>
      <div><b>库存智能提醒</b><p>温湿度传感器低于安全库存，建议结合待到货采购单及时补货。</p></div>
      <router-link to="/inventory"><van-icon name="arrow" /></router-link>
    </section>
  </div>
</template>

<style scoped>
.topbar{display:flex;align-items:center;justify-content:space-between;margin:1px 2px 18px}.identity{display:flex;align-items:center;gap:10px}.identity .mark{width:39px;height:39px;display:grid;place-items:center;background:var(--navy);border-radius:13px;color:#5be0d5;font-weight:850;font-size:12px}.identity div{display:flex;flex-direction:column}.identity b{font-size:15px;color:var(--navy)}.identity small{color:var(--muted);font-size:10px;margin-top:3px}.online{font-size:10px;color:#4a6b76;background:#fff;border:1px solid var(--line);padding:7px 10px;border-radius:999px}.online i{display:inline-block;width:6px;height:6px;margin-right:5px;border-radius:50%;background:#21b9a9}.hero-card{position:relative;overflow:hidden;padding:23px 21px 19px;border-radius:24px;color:#fff;background:linear-gradient(135deg,#092234,#16495b);box-shadow:0 17px 34px rgba(11,37,56,.22)}.hero-top,.hero-foot{position:relative;z-index:2;display:flex;align-items:center;justify-content:space-between}.hero-top{font-size:12px;color:#c1d6dc}.trend{padding:4px 8px;border-radius:999px;background:rgba(67,213,198,.16);color:#78e7dc}.hero-money{position:relative;z-index:2;margin:14px 0 17px;font-size:37px;font-weight:800;letter-spacing:-1.3px}.hero-money small{font-size:18px;margin-right:4px}.hero-foot{font-size:10px;color:#91b1bc}.orb{position:absolute;border-radius:50%;border:1px solid rgba(91,224,213,.17)}.orb-one{width:140px;height:140px;right:-35px;top:-60px}.orb-two{width:90px;height:90px;right:27px;top:-14px}.kpi-grid{display:grid;grid-template-columns:repeat(2,1fr);gap:10px;margin-top:13px}.kpi{display:flex;align-items:center;gap:10px;padding:13px;background:#fff;border-radius:17px;border:1px solid var(--line)}.kpi-icon{width:35px;height:35px;display:grid;place-items:center;border-radius:12px;font-size:18px}.kpi-icon.teal{color:#0b8e83;background:#dff6f2}.kpi-icon.orange{color:#d66b13;background:#fff0df}.kpi-icon.red{color:#d24e47;background:#ffedec}.kpi-icon.blue{color:#396f9b;background:#e8f1f8}.kpi div{display:flex;flex-direction:column}.kpi strong{font-size:19px;color:var(--navy)}.kpi small{color:var(--muted);font-size:10px;margin-top:2px}.module-item.disabled{opacity:.42}.cash-card{display:grid;grid-template-columns:1fr 1px 1fr;gap:16px;padding:19px;border-radius:21px;color:#fff;background:linear-gradient(125deg,#168f86,#1eb6a9)}.cash-card>div:not(.divider){display:flex;flex-direction:column}.cash-card span{font-size:11px;color:#ccf4f0}.cash-card strong{font-size:20px;margin:7px 0 4px}.cash-card small{font-size:9px;color:#b7e7e2}.divider{width:1px;background:rgba(255,255,255,.25)}.chart-card{height:170px}.bars{height:113px;display:flex;align-items:flex-end;justify-content:space-around;gap:10px;padding:6px 6px 0}.bars i{width:17px;display:flex;align-items:flex-end;border-radius:6px 6px 3px 3px;background:#e1eeee}.bars i span{width:100%;height:70%;border-radius:6px 6px 3px 3px;background:linear-gradient(180deg,#27bbae,#0d8e84)}.axis{display:flex;justify-content:space-around;color:#8da0a8;font-size:10px;margin-top:8px}.alert-card{display:flex;align-items:center;gap:12px;margin-top:15px;padding:15px;border-radius:18px;background:#fff8ef;border:1px solid #fee4c2}.alert-icon{flex:none;width:38px;height:38px;display:grid;place-items:center;border-radius:13px;background:#ffe7c7;color:#d36b15;font-size:20px}.alert-card b{font-size:13px;color:#613813}.alert-card p{margin:4px 0 0;color:#9a6d41;font-size:10px;line-height:1.45}.alert-card a{margin-left:auto;color:#b97231}.disabled{pointer-events:none}
</style>

