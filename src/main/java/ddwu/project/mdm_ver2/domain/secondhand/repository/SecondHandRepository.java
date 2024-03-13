package ddwu.project.mdm_ver2.domain.secondhand.repository;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecondHandRepository extends JpaRepository<SecondHand, Long> {

    List<SecondHand> findAllByOrderByIdDesc();

    List<SecondHand> findAllByOrderByPriceAsc();

    List<SecondHand> findAllByOrderByPriceDesc();

    List<SecondHand> findAllByCategoryCateCodeOrderByIdDesc(String cateCode);

    List<SecondHand> findAllByCategoryCateCodeOrderByPriceAsc(String cateCode);

    List<SecondHand> findAllByCategoryCateCodeOrderByPriceDesc(String cateCode);

    List<SecondHand> findByNameContainingIgnoreCase(String shName);

    Optional <SecondHand> findById(Long shId);

    SecondHand saveAndFlush(SecondHand secondHand);

    void deleteById(Long shId);

    List<SecondHand> findAllByUserId(Long userId);
}