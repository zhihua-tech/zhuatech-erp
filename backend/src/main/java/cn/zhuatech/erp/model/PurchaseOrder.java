/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "erp_purchase_order")
public class PurchaseOrder extends BaseEntity {
    public enum Status { DRAFT, APPROVED, RECEIVED, CANCELLED }

    @Column(nullable = false, unique = true, length = 40)
    private String orderNo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private BusinessPartner supplier;
    @Column(nullable = false)
    private LocalDate orderDate;
    private LocalDate expectedDate;
    @Column(nullable = false, length = 500)
    private String itemSummary;
    @Column(nullable = false)
    private int itemCount;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;
    @Column(length = 50)
    private String buyerName;

    protected PurchaseOrder() {}

    public PurchaseOrder(String orderNo, BusinessPartner supplier, LocalDate orderDate, LocalDate expectedDate,
                         String itemSummary, int itemCount, BigDecimal totalAmount, Status status, String buyerName) {
        this.orderNo = orderNo;
        this.supplier = supplier;
        this.orderDate = orderDate;
        this.expectedDate = expectedDate;
        this.itemSummary = itemSummary;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
        this.status = status;
        this.buyerName = buyerName;
    }

    public void changeStatus(Status status) { this.status = status; }
    public String getOrderNo() { return orderNo; }
    public BusinessPartner getSupplier() { return supplier; }
    public LocalDate getOrderDate() { return orderDate; }
    public LocalDate getExpectedDate() { return expectedDate; }
    public String getItemSummary() { return itemSummary; }
    public int getItemCount() { return itemCount; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Status getStatus() { return status; }
    public String getBuyerName() { return buyerName; }
}

