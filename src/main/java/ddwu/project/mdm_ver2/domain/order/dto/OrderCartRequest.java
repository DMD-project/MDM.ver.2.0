package ddwu.project.mdm_ver2.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartRequest {
    private List<Long> itemIds;
    private OrderDto orderDto;
}
