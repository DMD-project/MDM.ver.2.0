package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Cart;
import ddwu.project.mdm_ver2.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // 사용자 장바구니 조회
    @Transactional
    public Cart getCartByUser(long userCode) {
        return cartRepository.findByUser_UserCode(userCode);
    }

    //장바구니 전체 비우기
    @Transactional
    public void clearCart(Long userCode) {
        Cart cart = cartRepository.findByUser_UserCode(userCode);
        cartRepository.delete(cart);
    }

}
