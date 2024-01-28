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
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cartItemCount")
    private int cartItemCount; // 상품 개수

    @Column(name = "cartItemPrice")
    private int cartItemPrice; //상품금액

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "prod_id")
    private Product product;

    public CartItem(int cartItemCount, int cartItemPrice, Cart cart, Product product) {
        this.cartItemCount = cartItemCount;
        this.cartItemPrice = cartItemPrice;
        this.cart = cart;
        this.product = product;
    }

    public void addCount(int cartItemCount) {
        this.cartItemCount += cartItemCount;
    }
    public void addPrice(int cartItemPrice) {
        this.cartItemPrice += cartItemPrice;
    }
    public void subCount(int cartItemCount) {
        this.cartItemCount -= cartItemCount;
    }
    public void subPrice(int cartItemPrice) {
        this.cartItemPrice -= cartItemPrice;
    }

}
