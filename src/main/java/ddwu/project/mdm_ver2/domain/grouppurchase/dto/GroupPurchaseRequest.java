package ddwu.project.mdm_ver2.domain.grouppurchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupPurchaseRequest {
    private String name;      // 공동구매 이름
    private int price;        // 공동구매 가격
    private String content;   // 공동구매 내용
    private String imgUrl;     // 공동구매 이미지 URL
    private int maxQty;        //공동구매 최대 구매 가능 수량
    private int goalQty;       // 공동구매 목표 수량
    private Date start;      // 공동구매 시작일
    private Date end;        // 공동구매 종료일
    private String cateCode;    // 카테고리 코드
}
