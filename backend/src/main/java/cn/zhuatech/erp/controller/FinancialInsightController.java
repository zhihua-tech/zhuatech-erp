/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.erp.controller;

import cn.zhuatech.erp.common.ApiResponse;
import cn.zhuatech.erp.service.CashExposureService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/erp/insights")
public class FinancialInsightController {
    private final CashExposureService service;
    public FinancialInsightController(CashExposureService service) { this.service = service; }

    @PostMapping("/cash-exposure")
    public ApiResponse<CashExposureService.Result> analyze(@Valid @RequestBody CashExposureService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
