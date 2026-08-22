/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.erp;
import cn.zhuatech.erp.ai.OpenAiCompatibleGateway;
import cn.zhuatech.erp.service.AiInvoiceAnomalyService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
class AiInvoiceAnomalyServiceTests {
    private final AiInvoiceAnomalyService service = new AiInvoiceAnomalyService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));
    @Test void blocksDuplicateInvoiceWithChangedAccount() {
        var result = service.inspect(new AiInvoiceAnomalyService.Request("INV-2026-88", new BigDecimal("50000"),
            new BigDecimal("12000"), true, true, false, false, new BigDecimal("3"), 10));
        assertThat(result.decision()).isEqualTo("BLOCK");
        assertThat(result.anomalyScore()).isGreaterThanOrEqualTo(70);
    }
}
