package ddwu.project.mdm_ver2.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;

    @Column(name = "count")
    private int count; // 상품 개수

    @Column(name = "price")
    private int price; //상품금액

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setCount(0);
        cart.setUser(user);
        return cart;
    }

//    public int calculateTotalPrice() {
//        int totalPrice = 0;
//        for (CartItem cartItem : cartItems) {
//            totalPrice += cartItem.getPrice();
//        }
//        return totalPrice;
//    }
//    public void addCartItem(CartItem cartItem) {
//        cartItems.add(cartItem);
//        cartItem.setCart(this);
//        updateCartTotal();
//    }
//
//    public void removeCartItem(CartItem cartItem) {
//        cartItems.remove(cartItem);
//        cartItem.setCart(null);
//        updateCartTotal();
//    }
//
//    private void updateCartTotal() {
//        qty = cartItems.stream().mapToInt(CartItem::getQty).sum();
//        price = calculateTotalPrice();
//    }

}
