/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "erp_partner")
public class BusinessPartner extends BaseEntity {
    public enum Type { CUSTOMER, SUPPLIER }
    public enum Status { ACTIVE, SUSPENDED }

    @Column(nullable = false, unique = true, length = 40)
    private String code;
    @Column(nullable = false, length = 120)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;
    @Column(length = 50)
    private String contactName;
    @Column(length = 30)
    private String phone;
    @Column(length = 255)
    private String address;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal creditLimit;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    protected BusinessPartner() {}

    public BusinessPartner(String code, String name, Type type, String contactName, String phone,
                           String address, BigDecimal creditLimit) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.contactName = contactName;
        this.phone = phone;
        this.address = address;
        this.creditLimit = creditLimit;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public Type getType() { return type; }
    public String getContactName() { return contactName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public BigDecimal getCreditLimit() { return creditLimit; }
    public Status getStatus() { return status; }
}

