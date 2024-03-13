package ddwu.project.mdm_ver2.domain.secondhand.dto.sh;

import ddwu.project.mdm_ver2.domain.secondhand.dto.shBid.SecondHandBidResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandResponse {
    private Long shId;
    private Long userId; /* 중고거래 요청자 (가격 제안) */
    private String name;
    private String cateCode;
    private int price;
    private String imgUrl;
    private String content;
    private Character state;
    private List<SecondHandBidResponse> secondHandBidList;
    private Character favState;
    private Character userState; /* 작성자와 현재 사용자 일치 여부 */
    private Long selectBidId;
}