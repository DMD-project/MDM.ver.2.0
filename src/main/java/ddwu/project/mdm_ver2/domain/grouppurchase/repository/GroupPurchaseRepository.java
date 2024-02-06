package ddwu.project.mdm_ver2.domain.grouppurchase.repository;

import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Long> {
    List<GroupPurchase> findAllByOrderByPriceAsc(); // 낮은 가격순
    List<GroupPurchase> findAllByOrderByPriceDesc(); // 높은 가격순
    List<GroupPurchase> findAllByOrderByIdDesc();   //  최신순

    List<GroupPurchase> findAllByCategoryCateCodeOrderByPriceAsc(String cateCode);
    List<GroupPurchase> findAllByCategoryCateCodeOrderByPriceDesc(String cateCode);
    List<GroupPurchase> findAllByCategoryCateCodeOrderByIdDesc(String cateCode);
    List<GroupPurchase> findAllByCategoryCateCode(String cateCode);

    List<GroupPurchase> findByNameContainingIgnoreCase(String keyword);
}
