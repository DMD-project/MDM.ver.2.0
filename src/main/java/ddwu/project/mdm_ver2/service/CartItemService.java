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
import org.webjars.NotFoundException;

import java.util.Optional;

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

    // 장바구니품목 수량 감소
    
    //장바구니 품목 삭제



//    // 장바구니품목 삭제
//    @Transactional
//    public void deleteCartItem(Long id) {
//        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
//
//        CartItem cartItem = optionalCartItem.orElseThrow(() ->
//                new ResourceNotFoundException("CartItem", "id", id));
//
//        Cart cart = cartItem.getCart();
//        cart.setQty(cart.getQty() - cartItem.getQty());
//        cart.setPrice(cart.calculateTotalPrice());
//        cartRepository.save(cart);
//
//        cartItemRepository.delete(cartItem);
//    }
//
//    // 장바구니품목 수량
//    public CartItem updateCartItemQty(long cartItemId, int qty) {
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new NotFoundException("CartItem not found"));
//
//        int diff = qty - cartItem.getQty(); //새로운 수량
//
//
//        //장바구니품목 업데이트
//        cartItem.setQty(qty);
//        cartItemRepository.save(cartItem);
//
//
//        updateCartTotal(cartItem.getCart(), diff);
//
//        return cartItem;
//    }
//
//
//    //장바구니품목 수량
//    public Integer getCartItemQty(long cartItemId) {
//        return cartItemRepository.findQtyById(cartItemId);
//    }
//
//    // 장바구니의 총 가격과 총 수량 업데이트
//    private void addCartTotal(Cart cart, CartItem cartItem) {
//        int totalqty = cartItem.getQty() + cart.getQty();
//
//        cart.setQty(totalqty); // 총 수량 업데이트
//        cart.setPrice(cart.calculateTotalPrice());
//        cartRepository.save(cart);
//    }
//
//    //수량 증가, 감소 반영
//    private void updateCartTotal(Cart cart, int diff) {
//
//        cart.setQty(cart.getQty() + diff); // 총 수량 업데이트
//        cart.setPrice(cart.calculateTotalPrice());
//        cartRepository.save(cart);
//    }
}
