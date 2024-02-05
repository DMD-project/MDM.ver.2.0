package ddwu.project.mdm_ver2.domain.product.entity;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import ddwu.project.mdm_ver2.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@SuppressWarnings("serial")
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "prod_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cate_id", referencedColumnName = "cate_id")
    private Category category;

    @Column(name = "prod_name")
    private String name;

    @Column(name = "prod_price")
    private int price;

    @Column(name = "prod_content")
    private String content;

    @Column(name = "prod_img_url")
    private String imgUrl;

    @Column(name = "review_cnt")
    private int reviewCnt;

    @Column(name = "review_star_avg")
    private float reviewStarAvg;

    @Builder
    public Product(Category category, String name, int price, String content, String imgUrl) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public Product(Category category, String name, int price, String content) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.content = content;
    }
}
