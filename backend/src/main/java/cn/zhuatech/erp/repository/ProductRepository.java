/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.erp.repository;

import cn.zhuatech.erp.model.Product;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc();
    boolean existsBySku(String sku);
    @Query("select count(p) from Product p where p.enabled = true and p.stockOnHand <= p.safetyStock")
    long countLowStock();
}

