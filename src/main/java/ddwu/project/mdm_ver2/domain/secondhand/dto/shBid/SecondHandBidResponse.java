package ddwu.project.mdm_ver2.domain.secondhand.dto.shBid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandBidResponse {

    private Long bidId;
    private Long shId;
    private Long bidUserId;
    private int price;
    private Character bidUserState;

}
