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

    @Transactional
    Favorite saveAndFlush(Favorite favInfo); // 찜등록

    List<Favorite> findAllByProductId(Long prodId);

    @Transactional
    void deleteByUserEmailAndProductId(String userEmail, Long prodId); // 찜해제

}
