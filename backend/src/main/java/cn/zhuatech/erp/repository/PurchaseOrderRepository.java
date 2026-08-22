/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.repository;

import cn.zhuatech.erp.model.PurchaseOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    @EntityGraph(attributePaths = "supplier")
    List<PurchaseOrder> findAllByOrderByOrderDateDescIdDesc();
    @Override
    @EntityGraph(attributePaths = "supplier")
    Optional<PurchaseOrder> findById(Long id);
    boolean existsByOrderNo(String orderNo);
    long countByStatusIn(Collection<PurchaseOrder.Status> statuses);
}
