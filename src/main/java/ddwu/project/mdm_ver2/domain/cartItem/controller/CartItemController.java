package ddwu.project.mdm_ver2.domain.cartItem.controller;

import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.cartItem.repository.ItemsRepository;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.domain.cartItem.service.CartItemService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cartItem")
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ItemsRepository cartItemRepository;

    // 장바구니품목 추가
    @Transactional
    @PostMapping("add/{prodId}/{count}")
    public CustomResponse<Items> addItemToCart(Principal principal, @PathVariable("prodId") long prodId, @PathVariable("count") int count) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        Product product = productRepository.findById(prodId).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

        return cartItemService.addItemToCart(user, product, count);
    }

    // 장바구니품목 증가(1개씩)
    @Transactional
    @PutMapping("/increase/{itemsId}")
    public CustomResponse<Items> increaseItem(@PathVariable("itemsId") long itemsId) {
        Items cartItem = cartItemRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("장바구니 품목을 찾을 수 없습니다."));
        return cartItemService.increaseItem(cartItem);
    }

    // 장바구니품목 감소(1개씩)
    @Transactional
    @PutMapping("/decrease/{itemsId}")
    public CustomResponse<Items> decreaseItem(@PathVariable("itemsId") long itemsId) {
        Items cartItem = cartItemRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("장바구니 품목을 찾을 수 없습니다."));
        return cartItemService.decreaseItem(cartItem);
    }

    // 장바구니 선택한 품목들 삭제
    @Transactional
    @DeleteMapping("/delete/selected")
    public CustomResponse<Void> deleteCartItems(@RequestParam(name = "itemsId") List<Long> itemsId) {
        return cartItemService.deleteCartItems(itemsId);
    }

}
