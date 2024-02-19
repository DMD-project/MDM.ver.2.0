package ddwu.project.mdm_ver2.domain.order.controller;

import ddwu.project.mdm_ver2.domain.order.dto.OrderDto;
import ddwu.project.mdm_ver2.domain.order.entity.Order;
import ddwu.project.mdm_ver2.domain.order.service.OrderService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController implements OrderApi{

    private final OrderService orderService;

    // 주문 정보 추가
    @PostMapping("/add")
    public CustomResponse<Order> addOrder(@RequestBody OrderDto order) {
        return orderService.addOrder(order);
    }

    // 주문 정보 수정
    @PutMapping("/update/{orderId}")
    public CustomResponse<Order> updateOrder(@PathVariable("orderId") long orderId, @RequestBody OrderDto updatedOrder) {
        return orderService.updateOrder(orderId, updatedOrder);
    }

    //주문 취소
    @DeleteMapping("/cancel/{orderId}")
    public CustomResponse<Void> cancelOrder(@PathVariable("orderId") long orderId) {
        return orderService.cancelOrder(orderId);
    }

    //주문 상세 정보 조회
    @GetMapping("/{orderId}")
    public CustomResponse<Order> getOrderDetail(@PathVariable("orderId") long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    // 장바구니 항목 구매 추가
    @PostMapping("/cart/add")
    public CustomResponse<Order> purchaseItemsFromCart(Principal principal, @RequestBody List<Long> itemIds) {
        return orderService.purchaseItemsFromCart(principal.getName(), itemIds);
    }

    // 공동구매 항목 추가
    @PostMapping("/gp/add")
    public CustomResponse<Order> addGroupPurchaseToOrder(@RequestParam("orderId") Long orderId, @RequestParam("gpParticipantId") Long gpParticipantId) {
        return orderService.addGroupPurchaseToOrder(orderId, gpParticipantId);
    }
}
