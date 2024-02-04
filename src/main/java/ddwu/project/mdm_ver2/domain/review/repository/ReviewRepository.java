package ddwu.project.mdm_ver2.domain.review.repository;

import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long prodId);

    /* 최신순 */
    List<Review> findAllByProductIdOrderByIdDesc(Long prodId);

    /* 별점 낮은 순 */
    List<Review> findAllByProductIdOrderByStar(Long prodId);

    /* 별점 높은 순 */
    List<Review> findAllByProductIdOrderByStarDesc(Long prodId);

    Review saveAndFlush(Review review);

    void deleteById(Long reviewId);
}
