package ddwu.project.mdm_ver2.domain.cart.repository;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(long userId);

    Cart findByUserEmail(String userEmail);

    //    Optional<Cart> findByUser(User user);

//    Optional<Cart> findByUser_UserCode(long userCode);


//    @Query("SELECT c.qty FROM Cart c WHERE c.id = :id")
//    Integer findQtyById(@Param("id") Long id);

//    @Query("DELETE FROM cart_item c WHERE c.cart_id = :cartId")
//    @Modifying
//    @Transactional
//    void deleteItemsInCart(@Param("cartId") Long cartId);
}
