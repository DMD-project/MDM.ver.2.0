package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserIDAndProdID(Long userID, Long prodID); // get favState (exist->fav O)

    Favorite saveAndFlush(Favorite favInfo); // 찜등록

    @Transactional
    void deleteByUserIDAndProdID(Long userID, Long prodID); // 찜해제

}
