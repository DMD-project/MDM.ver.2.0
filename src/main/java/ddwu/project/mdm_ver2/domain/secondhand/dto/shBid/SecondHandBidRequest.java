package ddwu.project.mdm_ver2.domain.secondhand.dto.shBid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandBidRequest {
    private Long shId;
    private int price;
}