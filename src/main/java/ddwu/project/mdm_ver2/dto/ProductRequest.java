package ddwu.project.mdm_ver2.dto;

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
    private String brand;     // 상품 브랜드
    private String name;      // 상품 이름
    private int price;        // 상품 가격
    private String content;   // 상품 내용
    private String prodIMGUrl; // 상품 이미지 URL
}
