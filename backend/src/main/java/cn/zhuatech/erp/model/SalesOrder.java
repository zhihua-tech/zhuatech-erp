/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "erp_sales_order")
public class SalesOrder extends BaseEntity {
    public enum Status { DRAFT, CONFIRMED, SHIPPED, COMPLETED, CANCELLED }

    @Column(nullable = false, unique = true, length = 40)
    private String orderNo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private BusinessPartner customer;
    @Column(nullable = false)
    private LocalDate orderDate;
    @Column(nullable = false, length = 500)
    private String itemSummary;
    @Column(nullable = false)
    private int itemCount;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal totalAmount;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal paidAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;
    @Column(length = 50)
    private String ownerName;

    protected SalesOrder() {}

    public SalesOrder(String orderNo, BusinessPartner customer, LocalDate orderDate, String itemSummary,
                      int itemCount, BigDecimal totalAmount, BigDecimal paidAmount, Status status, String ownerName) {
        this.orderNo = orderNo;
        this.customer = customer;
        this.orderDate = orderDate;
        this.itemSummary = itemSummary;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.status = status;
        this.ownerName = ownerName;
    }

    public void changeStatus(Status status) { this.status = status; }
    public String getOrderNo() { return orderNo; }
    public BusinessPartner getCustomer() { return customer; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getItemSummary() { return itemSummary; }
    public int getItemCount() { return itemCount; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getPaidAmount() { return paidAmount; }
    public Status getStatus() { return status; }
    public String getOwnerName() { return ownerName; }
}

