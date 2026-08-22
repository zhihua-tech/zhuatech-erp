/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.repository;

import cn.zhuatech.erp.model.FinanceRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface FinanceRecordRepository extends JpaRepository<FinanceRecord, Long> {
    List<FinanceRecord> findAllByOrderByDueDateAscIdDesc();
    boolean existsByRecordNo(String recordNo);
    @Query("select coalesce(sum(f.amount - f.settledAmount), 0) from FinanceRecord f where f.type = :type and f.status <> cn.zhuatech.erp.model.FinanceRecord.Status.SETTLED")
    BigDecimal outstanding(@Param("type") FinanceRecord.Type type);
}

