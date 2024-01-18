package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.CartItem;
import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.repository.ProductRepository;
import ddwu.project.mdm_ver2.repository.UserRepository;
import ddwu.project.mdm_ver2.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@AllArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    // 장바구니품목 추가
    @Transactional
    @PostMapping("/{userCode}/{productId}/{count}")
    public void addItemToCart(@PathVariable("userCode") long userCode, @PathVariable("productId") long productId, @PathVariable("count") int count) {
        User user = userRepository.findByUserCode(userCode).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));

        cartItemService.addItemToCart(user, product, count);
    }

//    // 장바구니품목 제거
//    @DeleteMapping("/{cartItemId}")
//    public void deleteCartItem(@PathVariable("cartItemId") long cartItemId) {
//        cartItemService.deleteCartItem(cartItemId);
//    }
//
//    // 장바구니품목 수량
//    @GetMapping("/qty/{cartItemId}")
//    public Integer getCartQty(@PathVariable("cartItemId") long cartItemId) {
//        return cartItemService.getCartItemQty(cartItemId);
//    }
//
//    // 장바구니품목 수량 증가/감소
//    @PutMapping("/{cartItemId}/{qty}")
//    public CartItem updateItemQuantity(@PathVariable("cartItemId") long cartItemId, @PathVariable("qty") Integer qty) {
//        return cartItemService.updateCartItemQty(cartItemId, qty);
//    }

}
