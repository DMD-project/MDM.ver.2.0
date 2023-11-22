package ddwu.project.mdm_ver2.contorller.product;

import ddwu.project.mdm_ver2.domain.Product;
import lombok.Data;

import java.io.Serializable;

@Data
@SuppressWarnings("serial")
public class ProductForm  implements Serializable {
    private int id;
    private int cateID;
    private String name;
    private int brandID;
    private int price;
    private String content;
    private String prodImgUrl;

    private Product product;

    public ProductForm() {
        this.product = new Product(id, cateID, name, brandID, price, content);
    }
    public ProductForm(Product product) {
        this.product = product;
    }
}
