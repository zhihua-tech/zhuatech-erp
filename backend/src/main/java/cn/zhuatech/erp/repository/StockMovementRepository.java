/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.erp.repository;

import cn.zhuatech.erp.model.StockMovement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    @EntityGraph(attributePaths = "product")
    List<StockMovement> findTop30ByOrderByOccurredAtDesc();
    long countByOccurredAtBetween(LocalDateTime start, LocalDateTime end);
}
