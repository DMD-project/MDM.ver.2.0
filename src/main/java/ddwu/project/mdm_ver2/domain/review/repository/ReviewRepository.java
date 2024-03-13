package ddwu.project.mdm_ver2.domain.review.repository;

import ddwu.project.mdm_ver2.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long prodId);

    List<Review> findAllByProductIdOrderByIdDesc(Long prodId);

    List<Review> findAllByProductIdOrderByStar(Long prodId);

    List<Review> findAllByProductIdOrderByStarDesc(Long prodId);

    Review saveAndFlush(Review review);

    void deleteById(Long reviewId);

    List<Review> findAllByUserEmail(String userEmail);

    Long countByProductId(Long prodId);

    @Query(value = "select round(avg(review_star), 1) " +
            "from review " +
            "where prod_id=:prodId " +
            "group by prod_id", nativeQuery = true)
    float getReviewStarAvg(@Param("prodId")Long prodId);
}