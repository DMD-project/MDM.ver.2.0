package ddwu.project.mdm_ver2.domain;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cateId", referencedColumnName = "id")
    private Category category;

    @Column(name = "prodName")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "content")
    private String content;

    @Column(name = "prodIMGUrl")
    private String prodIMGUrl;

    @Builder
    public Product(Category category, String name,  int price, String content, String prodIMGUrl) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.content = content;
        this.prodIMGUrl = prodIMGUrl;
    }


    public Product(Category category,String name,  int price, String content) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.content = content;
    }
}
