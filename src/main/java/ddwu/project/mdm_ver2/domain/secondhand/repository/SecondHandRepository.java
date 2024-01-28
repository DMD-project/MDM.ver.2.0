package ddwu.project.mdm_ver2.domain.secondhand.repository;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecondHandRepository extends JpaRepository<SecondHand, Long> {

    /* 최신순 (최신순) */
    List<SecondHand> findAllByOrderByShIDDesc();

    /* 카테고리 해당 상품 정렬(기본) */
    List<SecondHand> findAllByCategoryCateCode(String cateCode);

    /* 카테고리 해당 상품 정렬 (최신순) */
    List<SecondHand> findAllByCategoryCateCodeOrderByShIDDesc(String cateCode);

    /* 카테고리 내 상품 정렬 (낮은순) */
    List<SecondHand> findAllByCategoryCateCodeOrderByShPriceAsc(String cateCode);

    /* 카테고리 내 상품 정렬 (높은순) */
    List<SecondHand> findAllByCategoryCateCodeOrderByShPriceDesc(String cateCode);

    /* 상품 이름 검색 */
    List<SecondHand> findByShNameContainingIgnoreCase(String shName);

    SecondHand findByShID(Long shID);

    SecondHand saveAndFlush(SecondHand secondHand);

    void deleteByShID(Long shID);

    /* 요청 수 (가격 제안 댓글 수) */

}
