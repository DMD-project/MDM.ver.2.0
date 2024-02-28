package ddwu.project.mdm_ver2.domain.grouppurchase.dto;

import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupPurchaseResponse {
    private GroupPurchase groupPurchase;
    private Long participantsQty;
}
