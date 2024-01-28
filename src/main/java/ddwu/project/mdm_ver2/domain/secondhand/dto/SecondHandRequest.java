package ddwu.project.mdm_ver2.domain.secondhand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandRequest {

    private Long userID; /* 중고거래 요청자 (가격 제안) */
    private String shName;
    private String cateCode;
    private int shPrice;
    private String shImg;
    private String shContent;

}
