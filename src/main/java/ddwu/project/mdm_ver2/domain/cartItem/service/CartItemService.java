package ddwu.project.mdm_ver2.domain.cartItem.service;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.cartItem.repository.ItemsRepository;
import ddwu.project.mdm_ver2.domain.cart.repository.CartRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartItemService {
    private final ItemsRepository cartItemRepository;
    private final CartRepository cartRepository;

    // 장바구니품목 추가
    @Transactional
    public CustomResponse<Items> addItemToCart(User user, Product product, int count) {
        try {
            Cart cart = cartRepository.findByUserId(user.getId());

            int price = product.getPrice();

            // 장바구니가 존재하지 않는다면
            if (cart == null) {
                cart = Cart.createCart(user);
                cartRepository.save(cart);
            }

            Items cartItem = cartItemRepository.findByCartAndProduct(cart, product);

            if (cartItem != null) {
                cartItem.addCount(count);
                cartItem.addPrice(price * count);
            } else {
                cartItem = new Items(count, price * count, cart, product);
                cartItemRepository.save(cartItem);
            }
            cart.setCount(cart.getCount() + count);
            cart.setPrice(cart.getPrice() + (count * price));

            return CustomResponse.onSuccess(cartItem);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 장바구니품목 증가(1개씩)
    @Transactional
    public CustomResponse<Items> increaseItem(Items cartItem) {
        try {
            int price = cartItem.getProduct().getPrice();
            Cart cart = cartItem.getCart();

            cartItem.addCount(1);
            cartItem.addPrice(cartItem.getProduct().getPrice());
            cart.setCount(cart.getCount() + 1);
            cart.setPrice(cart.getPrice() + (1 * price));

            Items updatedCartItem = cartItemRepository.save(cartItem);

            return CustomResponse.onSuccess(updatedCartItem);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 장바구니품목 감소(1개씩)
    @Transactional
    public CustomResponse<Items> decreaseItem(Items cartItem) {
        try {
            int price = cartItem.getProduct().getPrice();
            Cart cart = cartItem.getCart();

            cartItem.subCount(1);
            cartItem.subPrice(cartItem.getProduct().getPrice());

            cart.setCount(cart.getCount() - 1);
            cart.setPrice(cart.getPrice() - (1 * price));

            Items updatedCartItem = cartItemRepository.save(cartItem);

            return CustomResponse.onSuccess(updatedCartItem);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 장바구니 품목 삭제
    @Transactional
    public CustomResponse<Void> deleteCartItems(List<Long> itemsIds) {
        try {
            for (Long itemsId : itemsIds) {
                Items cartItem = cartItemRepository.findById(itemsId)
                        .orElseThrow(() -> new IllegalArgumentException("장바구니품목을 찾을 수 없습니다. " + itemsId));

                Cart cart = cartItem.getCart();
                int itemCount = cartItem.getCount();
                int itemPrice = cartItem.getPrice();

                cart.setCount(cart.getCount() - itemCount);
                cart.setPrice(cart.getPrice() - itemPrice);

                cartItemRepository.delete(cartItem);
            }
            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

}
