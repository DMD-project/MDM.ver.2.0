package ddwu.project.mdm_ver2.domain.grouppurchase.repository;

import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Long> {

    List<GroupPurchase> findAllByOrderByPriceAsc();

    List<GroupPurchase> findAllByOrderByPriceDesc();

    List<GroupPurchase> findAllByOrderByIdDesc();

    List<GroupPurchase> findAllByCategoryCateCodeOrderByPriceAsc(String cateCode);

    List<GroupPurchase> findAllByCategoryCateCodeOrderByPriceDesc(String cateCode);

    List<GroupPurchase> findAllByCategoryCateCodeOrderByIdDesc(String cateCode);

    List<GroupPurchase> findAllByCategoryCateCode(String cateCode);

    List<GroupPurchase> findByNameContainingIgnoreCase(String keyword);
}