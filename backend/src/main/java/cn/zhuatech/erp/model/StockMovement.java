/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.erp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "erp_stock_movement")
public class StockMovement extends BaseEntity {
    public enum Type { INBOUND, OUTBOUND, ADJUSTMENT }

    @Column(nullable = false, unique = true, length = 40)
    private String movementNo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;
    @Column(nullable = false, precision = 14, scale = 3)
    private BigDecimal quantity;
    @Column(nullable = false, precision = 14, scale = 3)
    private BigDecimal beforeQuantity;
    @Column(nullable = false, precision = 14, scale = 3)
    private BigDecimal afterQuantity;
    @Column(length = 40)
    private String referenceNo;
    @Column(length = 50)
    private String handledBy;
    @Column(nullable = false)
    private LocalDateTime occurredAt;

    protected StockMovement() {}

    public StockMovement(String movementNo, Product product, Type type, BigDecimal quantity,
                         BigDecimal beforeQuantity, BigDecimal afterQuantity, String referenceNo,
                         String handledBy, LocalDateTime occurredAt) {
        this.movementNo = movementNo;
        this.product = product;
        this.type = type;
        this.quantity = quantity;
        this.beforeQuantity = beforeQuantity;
        this.afterQuantity = afterQuantity;
        this.referenceNo = referenceNo;
        this.handledBy = handledBy;
        this.occurredAt = occurredAt;
    }

    public String getMovementNo() { return movementNo; }
    public Product getProduct() { return product; }
    public Type getType() { return type; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getBeforeQuantity() { return beforeQuantity; }
    public BigDecimal getAfterQuantity() { return afterQuantity; }
    public String getReferenceNo() { return referenceNo; }
    public String getHandledBy() { return handledBy; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}

