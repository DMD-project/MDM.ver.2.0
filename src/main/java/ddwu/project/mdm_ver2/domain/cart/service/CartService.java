package ddwu.project.mdm_ver2.domain.cart.service;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.cart.repository.CartRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // 사용자 장바구니 조회
    @Transactional
    public CustomResponse<Cart> getCartByUser(long userCode) {
        Cart cart = cartRepository.findByUser_UserCode(userCode);
        if (cart == null) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), "사용자 코드 " + userCode + "에 대한 장바구니를 찾을 수 없습니다.");
        }
        return CustomResponse.onSuccess(cart);
    }

    //장바구니 전체 비우기
    @Transactional
    public CustomResponse<Void> clearCart(Long userCode) {
        Cart cart = cartRepository.findByUser_UserCode(userCode);
        if (cart == null) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), "사용자 코드 " + userCode + "에 대한 장바구니를 찾을 수 없습니다.");
        }
        cartRepository.delete(cart);
        return CustomResponse.onSuccess(null);
    }

}
