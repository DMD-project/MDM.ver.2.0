package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Cart;
import ddwu.project.mdm_ver2.domain.CartItem;
import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.repository.CartItemRepository;
import ddwu.project.mdm_ver2.repository.CartRepository;
import ddwu.project.mdm_ver2.repository.ProductRepository;
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

        //유저 Usercode로 해당 유저의 장바구니 찾기
        Cart cart = cartRepository.findByUser_UserCode(user.getUserCode());

        int price = product.getPrice();

        // 장바구니가 존재하지 않는다면
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        // 기존 장바구니에 해당 상품이 이미 존재하는지 확인
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItem != null) {
            // 상품이 이미 존재하는 경우
            cartItem.addCount(count);
            cartItem.addPrice(price * count);
        } else {
            // 상품이 존재하지 않는 경우 새로운 CartItem을 생성하여 추가
            cartItem = new CartItem(count, price * count , cart, product);
            cartItemRepository.save(cartItem);
        }
        // 카트 상품 총 개수 증가
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
