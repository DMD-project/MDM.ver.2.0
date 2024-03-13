package ddwu.project.mdm_ver2.domain.cart.controller;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.cart.service.CartService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController implements CartApi {

    private final CartService cartService;

    /* 사용자 장바구니 조회 */
    @GetMapping
    public CustomResponse<Cart> getCartByUser(Principal principal) {
        return cartService.getCartByUser(principal.getName());
    }

    /* 장바구니 비우기 */
    @DeleteMapping("/delete")
    public CustomResponse<Void> clearCart(Principal principal) {
        return cartService.clearCart(principal.getName());
    }
}