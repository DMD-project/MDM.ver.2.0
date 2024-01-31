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
    private String category;  // 상품 카테고리
    private String name;      // 상품 이름
    private int price;        // 상품 가격
    private String content;   // 상품 내용
    private String prodImgUrl; // 상품 이미지 URL
}
