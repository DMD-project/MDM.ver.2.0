package ddwu.project.mdm_ver2.domain.cartItem.repository;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {

    Items findByCartAndProduct(Cart cart, Product product);

    Optional<Items> findById(Long itemsId);
}
