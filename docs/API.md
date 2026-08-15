# ZhuaTech ERP REST API

Copyright © 2026 上海如静知华信息科技有限公司。

基础路径为 `/api`。除登录外，所有请求需携带 `Authorization: Bearer <token>`。响应统一为：

```json
{
  "success": true,
  "message": "操作成功",
  "data": {},
  "timestamp": "2026-07-26T02:00:00Z"
}
```

## 认证

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/auth/login` | 用户登录并签发 JWT |
| GET | `/auth/me` | 当前用户信息 |

## 经营与基础资料

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/erp/dashboard` | 经营驾驶舱指标 |
| GET | `/erp/products` | 商品列表 |
| POST | `/erp/products` | 新建商品，管理员/经理 |
| GET | `/erp/partners?type=CUSTOMER` | 客户或供应商列表 |
| POST | `/erp/partners` | 新建商业伙伴，管理员/经理 |

## 销售与采购

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/erp/sales-orders` | 销售订单列表 |
| POST | `/erp/sales-orders` | 新建销售订单 |
| PATCH | `/erp/sales-orders/{id}/status` | 更新销售状态 |
| GET | `/erp/purchase-orders` | 采购订单列表 |
| POST | `/erp/purchase-orders` | 新建采购订单 |
| PATCH | `/erp/purchase-orders/{id}/status` | 更新采购状态 |

## 库存与财务

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/erp/stock-movements` | 最近 30 条库存流水 |
| POST | `/erp/stock-movements` | 入库、出库或盘点调整 |
| GET | `/erp/finance-records` | 财务往来列表 |
| POST | `/erp/finance-records` | 新建应收、应付、费用或收款记录 |
| PATCH | `/erp/finance-records/{id}/settle` | 核销财务单据 |

## 示例

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo","password":"Demo@2026"}'
```

接口仅作为社区源码第一版学习样例。商业使用、接口扩展和生产级安全加固须取得上海如静知华信息科技有限公司书面授权，详见项目根目录 [LICENSE](../LICENSE)。

## 现金敞口分析

`POST /api/erp/insights/cash-exposure`：根据应收、逾期应收、应付、现金余额和固定成本返回营运资金、逾期率、现金跑道与风险动作。

## 营运资金周期

`POST /api/erp/insights/working-capital`：返回应收、应付、库存周转天数、现金转换周期及对应改善动作。
