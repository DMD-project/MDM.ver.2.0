package ddwu.project.mdm_ver2.domain.secondhand.dto.sh;

import ddwu.project.mdm_ver2.domain.secondhand.dto.shBid.SecondHandBidDto;
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

    private Long userId; /* 중고거래 요청자 (가격 제안) */
    private String name;
    private String cateCode;
    private int price;
    private String imgUrl;
    private String content;
    private List<SecondHandBidDto> secondHandBidList;
    private Character favState;
    private Character userState; /* 로그인 X, 작성자와 현재 사용자 다름 ('n') */
}
