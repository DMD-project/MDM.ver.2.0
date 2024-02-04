package ddwu.project.mdm_ver2.domain.cart.repository;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(long userId);
    Cart findByUserEmail(String userEmail);

}
