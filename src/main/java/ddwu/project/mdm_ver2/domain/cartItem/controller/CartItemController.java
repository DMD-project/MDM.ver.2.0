package ddwu.project.mdm_ver2.domain.cartItem.controller;

import ddwu.project.mdm_ver2.domain.cartItem.entity.CartItem;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.cartItem.repository.CartItemRepository;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.domain.cartItem.service.CartItemService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니품목 추가
    @Transactional
    @PostMapping("add/{userCode}/{productId}/{count}")
    public CustomResponse<CartItem> addItemToCart(@PathVariable("userCode") long userCode, @PathVariable("productId") long productId, @PathVariable("count") int count) {
        User user = userRepository.findByUserCode(userCode).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

        return cartItemService.addItemToCart(user, product, count);
    }

    // 장바구니품목 증가(1개씩)
    @Transactional
    @PutMapping("/increase/{cartItemId}")
    public CustomResponse<CartItem> increaseItem(@PathVariable("cartItemId") long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("장바구니 품목을 찾을 수 없습니다."));
        return cartItemService.increaseItem(cartItem);
    }

    // 장바구니품목 감소(1개씩)
    @Transactional
    @PutMapping("/decrease/{cartItemId}")
    public CustomResponse<CartItem> decreaseItem(@PathVariable("cartItemId") long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("장바구니 품목을 찾을 수 없습니다."));
        return cartItemService.decreaseItem(cartItem);
    }

    // 장바구니 선택한 품목들 삭제
    @Transactional
    @DeleteMapping("/delete/selected")
    public CustomResponse<Void> deleteCartItems(@RequestParam(name = "cartItemIds") List<Long> cartItemIds) {
        return cartItemService.deleteCartItems(cartItemIds);
    }

}
