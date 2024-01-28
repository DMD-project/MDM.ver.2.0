package ddwu.project.mdm_ver2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandReqRequest {

    private Long shReqID;
    private Long shID;
    private Long shReqUserID;
    private int shReqPrice;

}
