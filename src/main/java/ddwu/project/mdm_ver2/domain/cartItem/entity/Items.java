package ddwu.project.mdm_ver2.domain.cartItem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Items {
    @Id
    @SequenceGenerator(
            name = "items_seq_generator",
            sequenceName = "ITEMS_SEQ",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "items_seq_generator"
    )
    @Column(name = "items_id")
    private long id;

    @Column(name = "items_count")
    private int count; // 상품 개수

    @Column(name = "items_price")
    private int price; //상품금액

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "prod_id")
    private Product product;

    public Items(int count, int price, Cart cart, Product product) {
        this.count = count;
        this.price = price;
        this.cart = cart;
        this.product = product;
    }

    public void addCount(int count) {
        this.count += count;
    }
    public void addPrice(int price) {
        this.price += price;
    }
    public void subCount(int count) {
        this.count -= count;
    }
    public void subPrice(int price) {
        this.price -= price;
    }

}
