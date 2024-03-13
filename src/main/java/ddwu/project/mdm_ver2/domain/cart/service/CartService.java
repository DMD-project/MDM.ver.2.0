package ddwu.project.mdm_ver2.domain.cart.service;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.cart.repository.CartRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public CustomResponse<Cart> getCartByUser(String userEmail) {
        Cart cart = cartRepository.findByUserEmail(userEmail);
        if (cart == null) {
            return CustomResponse.onSuccess("장바구니에 상품이 없습니다. 첫 상품을 추가해보세요.");
        }
        return CustomResponse.onSuccess(cart);
    }

    @Transactional
    public CustomResponse<Void> clearCart(String userEmail) {
        Cart cart = cartRepository.findByUserEmail(userEmail);
        if (cart == null) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), "사용자 이메일 " + userEmail + "에 대한 장바구니를 찾을 수 없습니다.");
        }
        cartRepository.delete(cart);
        return CustomResponse.onSuccess(null);
    }
}