package ddwu.project.mdm_ver2.domain.cartItem.service;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.cartItem.entity.CartItem;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.cartItem.repository.CartItemRepository;
import ddwu.project.mdm_ver2.domain.cart.repository.CartRepository;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }


    // 장바구니품목 추가
    @Transactional
    public CartItem addItemToCart(User user, Product product, int count) {

        Cart cart = cartRepository.findByUser_UserCode(user.getUserCode());

        int price = product.getPrice();

        // 장바구니가 존재하지 않는다면
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItem != null) {
            cartItem.addCount(count);
            cartItem.addPrice(price * count);
        } else {
            cartItem = new CartItem(count, price * count , cart, product);
            cartItemRepository.save(cartItem);
        }
        cart.setCartCount(cart.getCartCount() + count);
        cart.setCartPrice(cart.getCartPrice() + (count * price));

        return cartItem;
    }

    // 장바구니품목 증가(1개씩)
    @Transactional
    public CartItem increaseItem(CartItem cartItem) {
        int price = cartItem.getProduct().getPrice();
        Cart cart = cartItem.getCart();

        cartItem.addCount(1);
        cartItem.addPrice(cartItem.getProduct().getPrice());

        cart.setCartCount(cart.getCartCount() + 1);
        cart.setCartPrice(cart.getCartPrice() + (1 * price));

        return cartItemRepository.save(cartItem);
    }

    // 장바구니품목 감소(1개씩)
    @Transactional
    public CartItem decreaseItem(CartItem cartItem) {
        int price = cartItem.getProduct().getPrice();
        Cart cart = cartItem.getCart();

        cartItem.subCount(1);
        cartItem.subPrice(cartItem.getProduct().getPrice());

        cart.setCartCount(cart.getCartCount() - 1);
        cart.setCartPrice(cart.getCartPrice() - (1 * price));

        return cartItemRepository.save(cartItem);
    }

    // 장바구니 품목 삭제
    @Transactional
    public void deleteCartItems(List<Long> cartItemIds) {
        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new IllegalArgumentException("장바구니품목을 찾을 수 없습니다. " + cartItemId));

            Cart cart = cartItem.getCart();
            int itemCount = cartItem.getCartItemCount();
            int itemPrice = cartItem.getCartItemPrice();

            cart.setCartCount(cart.getCartCount() - itemCount);
            cart.setCartPrice(cart.getCartPrice() - itemPrice);

            cartItemRepository.delete(cartItem);
        }
    }

}
