package ddwu.project.mdm_ver2.domain.grouppurchase.dto;

import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupPurchaseDto {
    private GroupPurchase groupPurchase;
    private int participantCount;
}
