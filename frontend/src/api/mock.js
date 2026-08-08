/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
const delay = value => new Promise(resolve => setTimeout(() => resolve(value), 90))

const buildMaterialPlan = ({ items }) => {
  const planned = items.map(item => {
    const projectedStock = item.onHand + item.inboundQty - item.forecastDemand
    const suggestedOrder = Math.max(0, item.safetyStock + item.forecastDemand - item.onHand - item.inboundQty)
    const riskLevel = projectedStock < 0 || (item.leadDays >= 14 && projectedStock < item.safetyStock) ? 'HIGH' : projectedStock < item.safetyStock ? 'MEDIUM' : 'LOW'
    return { ...item, projectedStock, suggestedOrder, riskLevel, action: riskLevel === 'HIGH' ? '立即锁定供应商交期并创建补货单' : riskLevel === 'MEDIUM' ? '纳入本周补货评审并跟踪在途数量' : '维持当前补货节奏' }
  }).sort((a, b) => ({ HIGH: 3, MEDIUM: 2, LOW: 1 }[b.riskLevel] - { HIGH: 3, MEDIUM: 2, LOW: 1 }[a.riskLevel]))
  const shortageItems = planned.filter(item => item.riskLevel !== 'LOW').length
  return { items: planned, shortageItems, capitalRequired: planned.reduce((sum, item) => sum + item.suggestedOrder * item.unitCost, 0), recommendation: shortageItems ? '优先下单高风险物料，并同步校验供应商承诺交期' : '库存结构健康，按周复核需求预测' }
}

const products = [
  { id: 4, sku: 'ZH-SV-001', name: '数字化实施服务包', category: '专业服务', unit: '项', costPrice: 12000, salePrice: 26000, stockOnHand: 9, safetyStock: 3, lowStock: false },
  { id: 3, sku: 'ZH-SW-ERP', name: '企业协同软件授权', category: '软件服务', unit: '套', costPrice: 4500, salePrice: 8800, stockOnHand: 48, safetyStock: 10, lowStock: false },
  { id: 2, sku: 'ZH-SN-016', name: '温湿度传感器', category: '智能硬件', unit: '只', costPrice: 96, salePrice: 168, stockOnHand: 12, safetyStock: 30, lowStock: true },
  { id: 1, sku: 'ZH-GW-001', name: '工业边缘网关 Pro', category: '智能硬件', unit: '台', costPrice: 1680, salePrice: 2680, stockOnHand: 86, safetyStock: 20, lowStock: false }
]

const partners = [
  { id: 1, code: 'C-1001', name: '海岳智能制造有限公司', type: 'CUSTOMER', contactName: '刘经理', phone: '13800001001', address: '上海市浦东新区', creditLimit: 500000, status: 'ACTIVE' },
  { id: 2, code: 'C-1002', name: '星云数科（上海）有限公司', type: 'CUSTOMER', contactName: '周女士', phone: '13800001002', address: '上海市闵行区', creditLimit: 300000, status: 'ACTIVE' },
  { id: 3, code: 'S-2001', name: '华东电子供应链有限公司', type: 'SUPPLIER', contactName: '赵主管', phone: '13900002001', address: '江苏省苏州市', creditLimit: 0, status: 'ACTIVE' },
  { id: 4, code: 'S-2002', name: '云桥软件技术有限公司', type: 'SUPPLIER', contactName: '孙经理', phone: '13900002002', address: '浙江省杭州市', creditLimit: 0, status: 'ACTIVE' }
]

const salesOrders = [
  { id: 3, orderNo: 'SO20260726003', customerName: '海岳智能制造有限公司', orderDate: '2026-07-26', itemSummary: '温湿度传感器 × 60', itemCount: 1, totalAmount: 10080, paidAmount: 0, unpaidAmount: 10080, status: 'DRAFT', ownerName: '王经理' },
  { id: 2, orderNo: 'SO20260726002', customerName: '星云数科（上海）有限公司', orderDate: '2026-07-25', itemSummary: '企业协同软件授权 × 8', itemCount: 1, totalAmount: 70400, paidAmount: 70400, unpaidAmount: 0, status: 'SHIPPED', ownerName: '陈销售' },
  { id: 1, orderNo: 'SO20260726001', customerName: '海岳智能制造有限公司', orderDate: '2026-07-23', itemSummary: '工业边缘网关 Pro × 20；数字化实施服务包 × 1', itemCount: 2, totalAmount: 79600, paidAmount: 30000, unpaidAmount: 49600, status: 'CONFIRMED', ownerName: '陈销售' }
]

const purchaseOrders = [
  { id: 2, orderNo: 'PO20260726002', supplierName: '云桥软件技术有限公司', orderDate: '2026-07-26', expectedDate: '2026-08-07', itemSummary: '软件组件年度技术支持', itemCount: 1, totalAmount: 38000, status: 'DRAFT', buyerName: '王经理' },
  { id: 1, orderNo: 'PO20260726001', supplierName: '华东电子供应链有限公司', orderDate: '2026-07-24', expectedDate: '2026-07-31', itemSummary: '温湿度传感器 × 200', itemCount: 1, totalAmount: 19200, status: 'APPROVED', buyerName: '王经理' }
]

const stockMovements = [
  { id: 3, movementNo: 'MV20260726003', sku: 'ZH-SN-016', productName: '温湿度传感器', type: 'ADJUSTMENT', quantity: 2, beforeQuantity: 10, afterQuantity: 12, referenceNo: 'STOCKTAKE-0726', handledBy: '王经理', occurredAt: '2026-07-26T14:25:00' },
  { id: 2, movementNo: 'MV20260726002', sku: 'ZH-SW-ERP', productName: '企业协同软件授权', type: 'OUTBOUND', quantity: 8, beforeQuantity: 56, afterQuantity: 48, referenceNo: 'SO20260726002', handledBy: '仓库管理员', occurredAt: '2026-07-26T13:00:00' },
  { id: 1, movementNo: 'MV20260726001', sku: 'ZH-GW-001', productName: '工业边缘网关 Pro', type: 'OUTBOUND', quantity: 20, beforeQuantity: 106, afterQuantity: 86, referenceNo: 'SO20260726001', handledBy: '仓库管理员', occurredAt: '2026-07-26T12:00:00' }
]

const financeRecords = [
  { id: 4, recordNo: 'EX20260726001', type: 'EXPENSE', partnerName: '知华科技', amount: 6800, settledAmount: 0, outstandingAmount: 6800, dueDate: '2026-07-29', status: 'PENDING', referenceNo: 'EXP-0726', remark: '项目差旅与现场实施费用' },
  { id: 2, recordNo: 'AR20260726002', type: 'RECEIVABLE', partnerName: '星云数科（上海）有限公司', amount: 70400, settledAmount: 70400, outstandingAmount: 0, dueDate: '2026-07-31', status: 'SETTLED', referenceNo: 'SO20260726002', remark: '已全额回款' },
  { id: 3, recordNo: 'AP20260726001', type: 'PAYABLE', partnerName: '华东电子供应链有限公司', amount: 19200, settledAmount: 0, outstandingAmount: 19200, dueDate: '2026-08-07', status: 'PENDING', referenceNo: 'PO20260726001', remark: '到货验收后付款' },
  { id: 1, recordNo: 'AR20260726001', type: 'RECEIVABLE', partnerName: '海岳智能制造有限公司', amount: 79600, settledAmount: 30000, outstandingAmount: 49600, dueDate: '2026-08-15', status: 'PARTIAL', referenceNo: 'SO20260726001', remark: '项目首付款已到账' }
]

export const mockApi = {
  login: form => {
    if (!['demo', 'admin', 'sales'].includes(form.username)) return Promise.reject(new Error('账号不存在'))
    const user = form.username === 'admin'
      ? { id: 1, username: 'admin', fullName: '系统管理员', role: 'ADMIN', department: '数字化中心' }
      : form.username === 'sales'
        ? { id: 3, username: 'sales', fullName: '陈销售', role: 'SALES', department: '销售中心' }
        : { id: 2, username: 'demo', fullName: '王经理', role: 'MANAGER', department: '经营管理部' }
    return delay({ token: 'zhuatech-erp-demo-token', user })
  },
  me: () => delay({ id: 2, username: 'demo', fullName: '王经理', role: 'MANAGER', department: '经营管理部' }),
  dashboard: () => delay({ salesAmount: 160080, activeSalesOrders: 3, pendingPurchaseOrders: 2, lowStockProducts: 1, outstandingReceivable: 59680, outstandingPayable: 19200, todayStockMovements: 3 }),
  products: () => delay(products),
  partners: type => delay(type ? partners.filter(item => item.type === type) : partners),
  salesOrders: () => delay(salesOrders),
  purchaseOrders: () => delay(purchaseOrders),
  stockMovements: () => delay(stockMovements),
  financeRecords: () => delay(financeRecords),
  materialPlan: data => delay(buildMaterialPlan(data))
}
