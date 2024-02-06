package ddwu.project.mdm_ver2.domain.grouppurchase.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GroupPurchaseStatusUpdater {
    private final GroupPurchaseService groupPurchaseService;

    public GroupPurchaseStatusUpdater(GroupPurchaseService groupPurchaseService) {
        this.groupPurchaseService = groupPurchaseService;
    }

    @Scheduled(fixedRate = 3600000) // 매 시간마다 실행 (1시간 = 3600000밀리초)
    public void updateGroupPurchaseStatus() {
        groupPurchaseService.updateGroupPurchaseStatus();
    }
    
}
