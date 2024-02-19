package ddwu.project.mdm_ver2.domain.order.service;

import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.cartItem.repository.ItemsRepository;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchaseParticipant;
import ddwu.project.mdm_ver2.domain.grouppurchase.repository.GroupPurchaseParticipantRepository;
import ddwu.project.mdm_ver2.domain.order.dto.OrderDto;
import ddwu.project.mdm_ver2.domain.order.entity.Order;
import ddwu.project.mdm_ver2.domain.order.repository.OrderRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemsRepository itemsRepository;
    private final GroupPurchaseParticipantRepository groupPurchaseParticipantRepository;

    public CustomResponse<Order> addOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setName(orderDto.getName());
        order.setContact(orderDto.getContact());
        order.setEmail(orderDto.getEmail());
        order.setZipcode(orderDto.getZipcode());
        order.setStreetAddr(orderDto.getStreetAddr());
        order.setDetailAddr(orderDto.getDetailAddr());

        Order savedOrder = orderRepository.save(order);
        return CustomResponse.onSuccess(savedOrder);
    }

    public CustomResponse<Order> updateOrder(long orderId, OrderDto updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            existingOrder.setName(updatedOrder.getName());
            existingOrder.setContact(updatedOrder.getContact());
            existingOrder.setEmail(updatedOrder.getEmail());
            existingOrder.setZipcode(updatedOrder.getZipcode());
            existingOrder.setStreetAddr(updatedOrder.getStreetAddr());
            existingOrder.setDetailAddr(updatedOrder.getDetailAddr());

            Order savedOrder = orderRepository.save(existingOrder);
            return CustomResponse.onSuccess(savedOrder);
        } else {
            return CustomResponse.onFailure(404, "주문을 찾을 수 없습니다.");
        }
    }

    public CustomResponse<Void> cancelOrder(long orderId) {
        orderRepository.deleteById(orderId);
        return CustomResponse.onSuccess(null);
    }

    public CustomResponse<Order> getOrderDetail(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(order -> CustomResponse.onSuccess(order))
                .orElseGet(() -> CustomResponse.onFailure(404, "주문을 찾을 수 없습니다."));
    }

    public CustomResponse<List<Order>> getOrderByUser(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        if (!orders.isEmpty()) {
            return CustomResponse.onSuccess(orders);
        } else {
            return CustomResponse.onFailure(404, "주문을 찾을 수 없습니다.");
        }
    }

    public CustomResponse<Order> purchaseItemsFromCart(String userEmail, List<Long> itemIds) {
        Order order = new Order();
        order.setEmail(userEmail);

        int totalPrice = 0;

        for (Long itemId : itemIds) {
            Items item = itemsRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found with ID: " + itemId));
            item.setOrder(order);
            order.getCartItems().add(item);
            totalPrice += item.getPrice();
        }
        order.setPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        return CustomResponse.onSuccess(savedOrder);
    }

    public CustomResponse<Order> addGroupPurchaseToOrder(Long orderId, Long gpParticipantId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            Optional<GroupPurchaseParticipant> optionalParticipant = groupPurchaseParticipantRepository.findById(gpParticipantId);
            if (optionalParticipant.isPresent()) {
                GroupPurchaseParticipant participant = optionalParticipant.get();
                GroupPurchase groupPurchase = participant.getGroupPurchase();

                order.setGroupPurchase(groupPurchase);
                orderRepository.save(order);

                return CustomResponse.onSuccess(order);
            } else {
                return CustomResponse.onFailure(404, "해당 공동구매 참여자를 찾을 수 없습니다.");
            }
        } else {
            return CustomResponse.onFailure(404, "주문을 찾을 수 없습니다.");
        }
    }
}
