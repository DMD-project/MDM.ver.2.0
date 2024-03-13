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

    @Transactional
    public CustomResponse<Void> addItemToCart(User user, Product product, int count) {
        try {
            Cart cart = cartRepository.findByUserId(user.getId());

            int price = product.getPrice();

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

            return CustomResponse.onSuccess("상품이 장바구니에 추가되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @Transactional
    public CustomResponse<Void> increaseItem(Items cartItem) {
        try {
            int price = cartItem.getProduct().getPrice();
            Cart cart = cartItem.getCart();

            cartItem.addCount(1);
            cartItem.addPrice(cartItem.getProduct().getPrice());
            cart.setCount(cart.getCount() + 1);
            cart.setPrice(cart.getPrice() + (1 * price));

            Items updatedCartItem = cartItemRepository.save(cartItem);

            return CustomResponse.onSuccess("장바구니 상품 수량이 증가되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @Transactional
    public CustomResponse<Void> decreaseItem(Items cartItem) {
        try {
            int price = cartItem.getProduct().getPrice();
            Cart cart = cartItem.getCart();

            cartItem.subCount(1);
            cartItem.subPrice(cartItem.getProduct().getPrice());

            cart.setCount(cart.getCount() - 1);
            cart.setPrice(cart.getPrice() - (1 * price));

            Items updatedCartItem = cartItemRepository.save(cartItem);

            return CustomResponse.onSuccess("장바구니 상품 수량이 감소되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @Transactional
    public CustomResponse<Void> deleteCartItems(List<Long> itemsIds) {
        try {
            for (Long itemsId : itemsIds) {
                Items cartItem = cartItemRepository.findById(itemsId).orElse(null);

                if (cartItem == null) {
                    return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), itemsId + " 장바구니 품목을 찾을 수 없습니다." );
                }

                Cart cart = cartItem.getCart();
                int itemCount = cartItem.getCount();
                int itemPrice = cartItem.getPrice();

                cart.setCount(cart.getCount() - itemCount);
                cart.setPrice(cart.getPrice() - itemPrice);

                cartItemRepository.delete(cartItem);
            }
            return CustomResponse.onSuccess("장바구니 상품이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}