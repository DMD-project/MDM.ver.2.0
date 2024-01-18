package ddwu.project.mdm_ver2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Column(name = "count")
    private int cartItemCount; // 상품 개수

    @Column(name = "price")
    private int cartItemPrice; //상품금액

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_id")
    private Product product;

    public CartItem(int cartItemCount, int cartItemPrice, Cart cart, Product product) {
        this.cartItemCount = cartItemCount;
        this.cartItemPrice = cartItemPrice;
        this.cart = cart;
        this.product = product;
    }

//    public static CartItem createCartItem(Cart cart, Product product, int amount) {
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setProduct(product);
//        cartItem.setCartItemCount(amount);
//        return cartItem;
//    }

    // 이미 담겨있는 물건 또 담을 경우
    public void addCount(int cartItemCount) {
        this.cartItemCount += cartItemCount;
    }
    public void addPrice(int cartItemPrice) {
        this.cartItemPrice += cartItemPrice;
    }

}
