package ddwu.project.mdm_ver2.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String name;
    private String contact;
    private String zipcode;
    private String streetAddr;
    private String detailAddr;
}
