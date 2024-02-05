package ddwu.project.mdm_ver2.domain.favorite.repository;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserEmailAndProductId(String userEmail, Long prodId); // get favState (exist->fav O)

    boolean existsByUserEmailAndSecondHandId(String userEmail, Long shId);
//    boolean existsByUserEmailAndGroupPurchaseId(String userEmail, Long gpId);

    @Transactional
    Favorite saveAndFlush(Favorite favInfo); // 찜등록

    @Transactional
    void deleteByUserEmailAndProductId(String userEmail, Long prodId); // 찜해제

    @Transactional
    void deleteByUserEmailAndSecondHandId(String userEmail, Long shId);

//    @Transactional
//    void deleteByUserEmailAndGroupPurchaseId(String userEmail, Long gpId);

    List<Favorite> findAllByUserEmail(String userEmail);
}
