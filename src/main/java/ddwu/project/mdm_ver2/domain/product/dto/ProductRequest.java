package ddwu.project.mdm_ver2.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String category;
    private String name;
    private int price;
    private String content;
    private String prodImgUrl;
}