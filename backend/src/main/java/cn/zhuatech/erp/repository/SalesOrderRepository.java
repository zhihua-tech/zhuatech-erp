/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.erp.repository;

import cn.zhuatech.erp.model.SalesOrder;
import org.springframework.data.jpa.repository.*;
import java.math.BigDecimal;
import java.util.*;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    @EntityGraph(attributePaths = "customer")
    List<SalesOrder> findAllByOrderByOrderDateDescIdDesc();
    @Override
    @EntityGraph(attributePaths = "customer")
    Optional<SalesOrder> findById(Long id);
    boolean existsByOrderNo(String orderNo);
    long countByStatusIn(Collection<SalesOrder.Status> statuses);
    @Query("select coalesce(sum(s.totalAmount), 0) from SalesOrder s where s.status <> cn.zhuatech.erp.model.SalesOrder.Status.CANCELLED")
    BigDecimal sumActiveSales();
}
