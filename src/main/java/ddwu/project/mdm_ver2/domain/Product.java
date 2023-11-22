package ddwu.project.mdm_ver2.domain;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SuppressWarnings("serial")
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @SequenceGenerator(
            name = "prod_seq_generator",
            sequenceName = "prod_seq",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "prod_seq_generator"
    )
    private int id;

    @Column(name = "cateID")
    private int cateID;

    @Column(name = "brandID")
    private int brandID;

    @Column(name = "PRODNAME")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "content")
    private String content;

    @Column(name = "prodIMGUrl")
    private String prodIMGUrl;

    public Product(int id, int cateID, String name, int brandID, int price, String content, String prodIMGUrl) {
        this.id = id;
        this.cateID = cateID;
        this.name = name;
        this.brandID = brandID;
        this.price = price;
        this.content = content;
        this.prodIMGUrl = prodIMGUrl;
    }

    public Product(int id, int cateID, String name, int brandID, int price, String content) {
        this.id = id;
        this.cateID = cateID;
        this.name = name;
        this.brandID = brandID;
        this.price = price;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", cateID=" + cateID + ", name=" + name + ", brandID=" + brandID + ", price="
                + price + ", content=" + content + ", prodIMGUrl=" + prodIMGUrl + "]";
    }
}
