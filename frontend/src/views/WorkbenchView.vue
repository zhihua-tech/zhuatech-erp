<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
const groups = [
  { title: '供应链协同', note: '从订单到履约', tone: 'teal', items: [['销售订单','orders-o','/sales'],['采购订单','cart-o','/purchase'],['库存管理','logistics','/inventory'],['客户供应商','friends-o','/partners']] },
  { title: '财务与经营', note: '看清现金与利润', tone: 'orange', items: [['应收应付','balance-list-o','/finance'],['费用报销','bill-o',''],['经营报表','bar-chart-o',''],['资金计划','cash-back-record','']] },
  { title: '基础资料', note: '统一企业数据口径', tone: 'blue', items: [['商品资料','apps-o','/products'],['仓库资料','wap-nav',''],['价格策略','gold-coin-o',''],['组织权限','setting-o','']] }
]
</script>

<template>
  <div class="page safe-top">
    <div class="page-head"><div><h1 class="page-title">业务工作台</h1><div class="page-subtitle">跨部门流程，从这里开始</div></div><span class="head-action"><van-icon name="scan" size="20" /></span></div>
    <section class="process-card">
      <div class="process-title"><span>今日业务流</span><b>8 个节点</b></div>
      <div class="process-line"><i class="done"></i><span></span><i class="done"></i><span></span><i class="active"></i><span></span><i></i></div>
      <div class="process-label"><span>下单</span><span>审核</span><span>出入库</span><span>结算</span></div>
    </section>
    <section v-for="group in groups" :key="group.title" class="card group-card">
      <div class="group-head"><div><span class="group-dot" :class="group.tone"></span><b>{{ group.title }}</b></div><small>{{ group.note }}</small></div>
      <div class="module-grid">
        <component :is="item[2] ? 'router-link' : 'div'" v-for="item in group.items" :key="item[0]" :to="item[2]" class="module-item" :class="{ disabled: !item[2] }">
          <span class="module-icon" :class="group.tone === 'orange' ? 'orange' : group.tone === 'blue' ? 'blue' : ''"><van-icon :name="item[1]" /></span><span>{{ item[0] }}</span>
        </component>
      </div>
    </section>
    <div class="version">ZhuaTech ERP Community · v1.0.0</div>
  </div>
</template>

<style scoped>
.process-card{margin-bottom:16px;padding:18px;border-radius:20px;color:#fff;background:linear-gradient(135deg,var(--navy),#235166)}.process-title{display:flex;justify-content:space-between;font-size:12px;color:#bed1d8}.process-title b{color:#56d7cb}.process-line{display:grid;grid-template-columns:12px 1fr 12px 1fr 12px 1fr 12px;align-items:center;margin:21px 8px 8px}.process-line i{width:12px;height:12px;border:2px solid #6c8996;border-radius:50%}.process-line i.done{border-color:#42cabe;background:#42cabe}.process-line i.active{border-color:#f6a453;box-shadow:0 0 0 4px rgba(246,164,83,.17)}.process-line span{height:2px;background:#4d6e7d}.process-label{display:flex;justify-content:space-between;color:#a9c0c8;font-size:10px}.group-card{padding:18px 15px 20px}.group-head{display:flex;justify-content:space-between;align-items:center;margin-bottom:18px}.group-head>div{display:flex;align-items:center;gap:8px}.group-head b{font-size:15px;color:var(--navy)}.group-head small{font-size:10px;color:var(--muted)}.group-dot{width:7px;height:7px;border-radius:50%;background:var(--teal)}.group-dot.orange{background:var(--orange)}.group-dot.blue{background:#4d88b6}.disabled{opacity:.4;pointer-events:none}.version{text-align:center;color:#9aabb1;font-size:10px;margin:28px 0 8px}
</style>

