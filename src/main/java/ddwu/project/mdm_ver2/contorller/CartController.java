package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.Cart;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.repository.UserRepository;
import ddwu.project.mdm_ver2.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;

    // 사용자 장바구니 조회
    @GetMapping("/{userCode}")
    public Cart getCartByUser(@PathVariable("userCode") long userCode) {
        return cartService.getCartByUser(userCode);
    }

    // 장바구니 비우기
    @DeleteMapping("/{userCode}")
    public void clearCart(@PathVariable("userCode") Long userCode) {
        cartService.clearCart(userCode);
    }

}
