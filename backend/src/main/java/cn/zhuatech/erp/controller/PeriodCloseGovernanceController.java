/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.controller;

import cn.zhuatech.erp.common.ApiResponse;
import cn.zhuatech.erp.service.PeriodCloseGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/erp")
public class PeriodCloseGovernanceController {
    private final PeriodCloseGovernanceService service;
    public PeriodCloseGovernanceController(PeriodCloseGovernanceService service) { this.service = service; }

    @PostMapping("/period-close")
    public ApiResponse<PeriodCloseGovernanceService.Assessment> assess(
            @Valid @RequestBody PeriodCloseGovernanceService.Request request) {
        return ApiResponse.ok("ERP 期间关账评估完成", service.assess(request));
    }
}
