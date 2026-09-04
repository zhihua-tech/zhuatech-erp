/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManualJournalPostingGovernanceServiceTest {
    private final ManualJournalPostingGovernanceService service = new ManualJournalPostingGovernanceService();

    @Test void postsControlledManualJournal() {
        var result = service.assess(request(true, true, true));
        assertEquals(ManualJournalPostingGovernanceService.Decision.POST, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    @Test void reviewsJournalWithOperationalActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(ManualJournalPostingGovernanceService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    @Test void blocksUncontrolledManualJournal() {
        var result = service.assess(new ManualJournalPostingGovernanceService.Request("JE-003", "BATCH-003", 6,
                false, false, false, false, false, false, false, false, false, false, false, false, false,
                true, true, true));
        assertEquals(ManualJournalPostingGovernanceService.Decision.BLOCKED, result.decision());
        assertEquals(13, result.blockers().size());
    }

    private ManualJournalPostingGovernanceService.Request request(boolean reversal, boolean notice, boolean archive) {
        return new ManualJournalPostingGovernanceService.Request("JE-001", "BATCH-001", 6,
                true, true, true, true, true, true, true, true, true, true, true, true, true,
                reversal, notice, archive);
    }
}
