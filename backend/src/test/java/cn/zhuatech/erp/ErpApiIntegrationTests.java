/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.erp;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ErpApiIntegrationTests {
    @Autowired
    MockMvc mvc;

    @Test
    void managerCanReadBusinessDashboardAndModules() throws Exception {
        String token = login("demo", "Demo@2026", "MANAGER");
        mvc.perform(get("/api/erp/dashboard").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.salesAmount").value(160080.00))
            .andExpect(jsonPath("$.data.lowStockProducts").value(1));
        mvc.perform(get("/api/erp/sales-orders").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].orderNo").value("SO20260726003"));
        mvc.perform(get("/api/erp/finance-records").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void managerCanCreateInboundMovementAndStockIsUpdated() throws Exception {
        String token = login("demo", "Demo@2026", "MANAGER");
        mvc.perform(post("/api/erp/stock-movements").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":2,\"type\":\"INBOUND\",\"quantity\":50,\"referenceNo\":\"PO-TEST-001\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.beforeQuantity").value(12.000))
            .andExpect(jsonPath("$.data.afterQuantity").value(62.000));
    }

    @Test
    void salesRoleCannotCreateProductMasterData() throws Exception {
        String token = login("sales", "Demo@2026", "SALES");
        mvc.perform(post("/api/erp/products").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sku\":\"TEST-001\",\"name\":\"测试商品\",\"category\":\"测试\",\"unit\":\"件\",\"costPrice\":1,\"salePrice\":2,\"stockOnHand\":0,\"safetyStock\":0}"))
            .andExpect(status().isForbidden());
    }

    @Test
    void unauthenticatedBusinessRequestIsRejected() throws Exception {
        mvc.perform(get("/api/erp/dashboard")).andExpect(status().isForbidden());
    }

    private String login(String username, String password, String expectedRole) throws Exception {
        String body = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.user.role").value(expectedRole))
            .andReturn().getResponse().getContentAsString();
        return JsonPath.read(body, "$.data.token");
    }
}

