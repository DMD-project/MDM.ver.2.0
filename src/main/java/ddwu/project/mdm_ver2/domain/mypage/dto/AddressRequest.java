package ddwu.project.mdm_ver2.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private String streetAddr;
    private String detailAddr;
    private String zipcode;
}
