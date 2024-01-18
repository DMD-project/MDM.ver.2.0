package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.Cart;
import ddwu.project.mdm_ver2.domain.CartItem;
import ddwu.project.mdm_ver2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProduct(Cart cart, Product product);

//    @Query("SELECT c.qty FROM Cart c WHERE c.id = :id")
//    Integer findQtyById(@Param("id") Long id);
}
