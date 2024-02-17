package ddwu.project.mdm_ver2.domain.order.repository;

import ddwu.project.mdm_ver2.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
