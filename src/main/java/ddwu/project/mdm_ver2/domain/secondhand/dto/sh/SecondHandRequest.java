package ddwu.project.mdm_ver2.domain.secondhand.dto.sh;

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

    private Long userId; /* 중고거래 요청자 (가격 제안) */
    private String name;
    private String cateCode;
    private int price;
    private String imgUrl;
    private String content;

}
