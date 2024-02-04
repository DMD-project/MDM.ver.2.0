package ddwu.project.mdm_ver2.domain.product.dto;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String cateCode;
    private String name;
    private int price;
    private String content;
    private String imgUrl;
    private Character favState;
    private List<Review> reviewList;
    private int reviewCnt;
//    private int starTotal;
}
