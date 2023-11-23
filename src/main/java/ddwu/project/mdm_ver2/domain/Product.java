package ddwu.project.mdm_ver2.domain;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    public Product(int cateID, String name, int brandID, int price, String content, String prodIMGUrl) {
        this.cateID = cateID;
        this.name = name;
        this.brandID = brandID;
        this.price = price;
        this.content = content;
        this.prodIMGUrl = prodIMGUrl;
    }

    public Product( int cateID, String name, int brandID, int price, String content) {
        this.cateID = cateID;
        this.name = name;
        this.brandID = brandID;
        this.price = price;
        this.content = content;
    }
}
