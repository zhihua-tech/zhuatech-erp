/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.erp.controller;

import cn.zhuatech.erp.common.ApiResponse;
import cn.zhuatech.erp.service.InventoryTurnoverService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/erp/insights")
public class InventoryTurnoverController {
    private final InventoryTurnoverService service;
    public InventoryTurnoverController(InventoryTurnoverService service) { this.service = service; }

    @PostMapping("/inventory-turnover")
    public ApiResponse<InventoryTurnoverService.Result> analyze(@Valid @RequestBody InventoryTurnoverService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
