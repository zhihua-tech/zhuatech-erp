/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.dto;

import cn.zhuatech.erp.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;

public final class ErpDto {
    private ErpDto() {}

    public record DashboardView(
        BigDecimal salesAmount,
        long activeSalesOrders,
        long pendingPurchaseOrders,
        long lowStockProducts,
        BigDecimal outstandingReceivable,
        BigDecimal outstandingPayable,
        long todayStockMovements) {}

    public record ProductCreateRequest(
        @NotBlank @Size(max = 40) String sku,
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 60) String category,
        @NotBlank @Size(max = 20) String unit,
        @NotNull @DecimalMin("0") BigDecimal costPrice,
        @NotNull @DecimalMin("0") BigDecimal salePrice,
        @NotNull @DecimalMin("0") BigDecimal stockOnHand,
        @NotNull @DecimalMin("0") BigDecimal safetyStock) {}

    public record ProductView(Long id, String sku, String name, String category, String unit,
                              BigDecimal costPrice, BigDecimal salePrice, BigDecimal stockOnHand,
                              BigDecimal safetyStock, boolean lowStock, boolean enabled) {
        public static ProductView from(Product product) {
            return new ProductView(product.getId(), product.getSku(), product.getName(), product.getCategory(),
                product.getUnit(), product.getCostPrice(), product.getSalePrice(), product.getStockOnHand(),
                product.getSafetyStock(), product.getStockOnHand().compareTo(product.getSafetyStock()) <= 0,
                product.isEnabled());
        }
    }

    public record PartnerCreateRequest(
        @NotBlank @Size(max = 40) String code,
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Pattern(regexp = "CUSTOMER|SUPPLIER") String type,
        @Size(max = 50) String contactName,
        @Size(max = 30) String phone,
        @Size(max = 255) String address,
        @NotNull @DecimalMin("0") BigDecimal creditLimit) {}

    public record PartnerView(Long id, String code, String name, String type, String contactName,
                              String phone, String address, BigDecimal creditLimit, String status) {
        public static PartnerView from(BusinessPartner partner) {
            return new PartnerView(partner.getId(), partner.getCode(), partner.getName(),
                partner.getType().name(), partner.getContactName(), partner.getPhone(), partner.getAddress(),
                partner.getCreditLimit(), partner.getStatus().name());
        }
    }

    public record SalesOrderCreateRequest(
        @NotBlank @Size(max = 40) String orderNo,
        @NotNull Long customerId,
        @NotNull LocalDate orderDate,
        @NotBlank @Size(max = 500) String itemSummary,
        @Min(1) int itemCount,
        @NotNull @DecimalMin("0.01") BigDecimal totalAmount,
        @NotNull @DecimalMin("0") BigDecimal paidAmount) {}

    public record SalesOrderView(Long id, String orderNo, Long customerId, String customerName,
                                 LocalDate orderDate, String itemSummary, int itemCount,
                                 BigDecimal totalAmount, BigDecimal paidAmount, BigDecimal unpaidAmount,
                                 String status, String ownerName) {
        public static SalesOrderView from(SalesOrder order) {
            return new SalesOrderView(order.getId(), order.getOrderNo(), order.getCustomer().getId(),
                order.getCustomer().getName(), order.getOrderDate(), order.getItemSummary(), order.getItemCount(),
                order.getTotalAmount(), order.getPaidAmount(), order.getTotalAmount().subtract(order.getPaidAmount()),
                order.getStatus().name(), order.getOwnerName());
        }
    }

    public record PurchaseOrderCreateRequest(
        @NotBlank @Size(max = 40) String orderNo,
        @NotNull Long supplierId,
        @NotNull LocalDate orderDate,
        LocalDate expectedDate,
        @NotBlank @Size(max = 500) String itemSummary,
        @Min(1) int itemCount,
        @NotNull @DecimalMin("0.01") BigDecimal totalAmount) {}

    public record PurchaseOrderView(Long id, String orderNo, Long supplierId, String supplierName,
                                    LocalDate orderDate, LocalDate expectedDate, String itemSummary,
                                    int itemCount, BigDecimal totalAmount, String status, String buyerName) {
        public static PurchaseOrderView from(PurchaseOrder order) {
            return new PurchaseOrderView(order.getId(), order.getOrderNo(), order.getSupplier().getId(),
                order.getSupplier().getName(), order.getOrderDate(), order.getExpectedDate(), order.getItemSummary(),
                order.getItemCount(), order.getTotalAmount(), order.getStatus().name(), order.getBuyerName());
        }
    }

    public record OrderStatusRequest(@NotBlank String status) {}

    public record StockMovementCreateRequest(
        @NotNull Long productId,
        @NotBlank @Pattern(regexp = "INBOUND|OUTBOUND|ADJUSTMENT") String type,
        @NotNull @DecimalMin("0.001") BigDecimal quantity,
        @Size(max = 40) String referenceNo) {}

    public record StockMovementView(Long id, String movementNo, Long productId, String sku,
                                    String productName, String type, BigDecimal quantity,
                                    BigDecimal beforeQuantity, BigDecimal afterQuantity,
                                    String referenceNo, String handledBy, LocalDateTime occurredAt) {
        public static StockMovementView from(StockMovement movement) {
            return new StockMovementView(movement.getId(), movement.getMovementNo(),
                movement.getProduct().getId(), movement.getProduct().getSku(), movement.getProduct().getName(),
                movement.getType().name(), movement.getQuantity(), movement.getBeforeQuantity(),
                movement.getAfterQuantity(), movement.getReferenceNo(), movement.getHandledBy(),
                movement.getOccurredAt());
        }
    }

    public record FinanceCreateRequest(
        @NotBlank @Size(max = 40) String recordNo,
        @NotBlank @Pattern(regexp = "RECEIVABLE|PAYABLE|EXPENSE|RECEIPT") String type,
        @Size(max = 120) String partnerName,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        LocalDate dueDate,
        @Size(max = 40) String referenceNo,
        @Size(max = 500) String remark) {}

    public record FinanceSettleRequest(@NotNull @DecimalMin("0") BigDecimal settledAmount) {}

    public record FinanceView(Long id, String recordNo, String type, String partnerName,
                              BigDecimal amount, BigDecimal settledAmount, BigDecimal outstandingAmount,
                              LocalDate dueDate, String status, String referenceNo, String remark) {
        public static FinanceView from(FinanceRecord record) {
            return new FinanceView(record.getId(), record.getRecordNo(), record.getType().name(),
                record.getPartnerName(), record.getAmount(), record.getSettledAmount(),
                record.getAmount().subtract(record.getSettledAmount()), record.getDueDate(),
                record.getStatus().name(), record.getReferenceNo(), record.getRemark());
        }
    }
}

