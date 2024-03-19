package ddwu.project.mdm_ver2.domain.order.controller;

import ddwu.project.mdm_ver2.domain.order.dto.OrderAddrRequest;
import ddwu.project.mdm_ver2.domain.order.dto.OrderCartRequest;
import ddwu.project.mdm_ver2.domain.order.dto.OrderDto;
import ddwu.project.mdm_ver2.domain.order.entity.Order;
import ddwu.project.mdm_ver2.domain.order.service.OrderService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController implements OrderApi{

    private final OrderService orderService;

    /* 주문 정보 수정 */
    @PutMapping("/update/{orderId}")
    public CustomResponse<Void> updateOrder(@PathVariable("orderId") long orderId, @RequestBody OrderAddrRequest updatedOrder) {
        return orderService.updateOrder(orderId, updatedOrder);
    }

    /* 주문 취소 */
    @DeleteMapping("/cancel/{orderId}")
    public CustomResponse<Void> cancelOrder(@PathVariable("orderId") long orderId) {
        return orderService.cancelOrder(orderId);
    }

    /* 주문 상세 정보 조회 */
    @GetMapping("/{orderId}")
    public CustomResponse<Order> getOrderDetail(@PathVariable("orderId") long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    /* 장바구니 상품 구매 */
    @PostMapping("/cart/add")
    public CustomResponse<Void> purchaseItemsFromCart(Principal principal, @RequestBody OrderCartRequest cartRequest) {
        return orderService.purchaseItemsFromCart(principal.getName(), cartRequest);
    }

    /* 일반상품 바로 구매 */
    @PostMapping("/product/{productId}/{purchasedQty}")
    public CustomResponse<Void> purchaseItems(Principal principal, @PathVariable Long productId, @PathVariable int purchasedQty, @RequestBody OrderDto orderDto) {
        return orderService.purchaseItems(principal.getName(), productId, purchasedQty, orderDto);
    }
}