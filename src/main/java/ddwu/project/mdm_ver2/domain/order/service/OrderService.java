package ddwu.project.mdm_ver2.domain.order.service;

import ddwu.project.mdm_ver2.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

}
