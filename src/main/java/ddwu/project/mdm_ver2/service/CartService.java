package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Cart;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // 사용자 장바구니 생성
//    @Transactional
//    public Cart findOrCreateCart(User user) {
//        // 사용자의 장바구니를 찾음
//        return cartRepository.findByUser(user)
//                .orElseGet(() -> {
//                    // 장바구니가 없으면 새로운 장바구니 생성
//                    Cart newCart = new Cart();
//                    newCart.setUser(user);
//                    return cartRepository.save(newCart);
//                });
//    }


    // 사용자 장바구니 조회
    @Transactional
        public Cart getCartByUser(long userCode) {
        return cartRepository.findByUser_UserCode(userCode);
    }


//    //장바구니 비우기
//    @Transactional
//    public void clearCart(Long id) {
//        Cart cart = cartRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", id));
//
//        cartRepository.deleteItemsInCart(id);
//    }
//
//    // 장바구니 총 수량
//    @Transactional
//    public Integer getCartbyCount(long id) {
//        return cartRepository.findCoutById(id);
//    }

}
