/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.erp.repository;

import cn.zhuatech.erp.model.BusinessPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartnerRepository extends JpaRepository<BusinessPartner, Long> {
    List<BusinessPartner> findAllByOrderByNameAsc();
    List<BusinessPartner> findByTypeOrderByNameAsc(BusinessPartner.Type type);
    boolean existsByCode(String code);
}

