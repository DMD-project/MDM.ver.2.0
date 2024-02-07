package ddwu.project.mdm_ver2.domain.review.service;

import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.review.dto.ReviewRequest;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.review.repository.ReviewRepository;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /* 리뷰 정렬 */
    public CustomResponse<List<Review>> sortReview(Long prodId, String sort) {
        try {
            List<Review> sortedReviewList;

            switch (sort) {
                case "newest":
                    sortedReviewList = reviewRepository.findAllByProductIdOrderByIdDesc(prodId);
                    break;
                case "lowstar":
                    sortedReviewList = reviewRepository.findAllByProductIdOrderByStar(prodId);
                    break;
                case"highstar":
                    sortedReviewList = reviewRepository.findAllByProductIdOrderByStarDesc(prodId);
                    break;
                default:
                    sortedReviewList = reviewRepository.findAllByProductId(prodId);
                    break;
            }

            return CustomResponse.onSuccess(sortedReviewList);
        } catch(Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    /* 리뷰 등록 */
    public CustomResponse<Review> addReview(String userEmail, Long prodId, ReviewRequest request) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            Review review = Review.builder()
                    .id(request.getId())
                    .user(user)
                    .product(product)
                    .star(request.getStar())
                    .date(request.getDate())
                    .content(request.getContent())
                    .build();

            reviewRepository.saveAndFlush(review);

            setReviewCnt(product, product.getReviewCnt(), "add");
            product.setReviewStarAvg(reviewRepository.getReviewStarAvg(prodId));
            productRepository.saveAndFlush(product);

            return CustomResponse.onSuccess(review);

        } catch(Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 리뷰 수정 */
    public CustomResponse<Review> updateReview(Principal principal, Long prodId, Long reviewId, ReviewRequest request) {
        try {
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NotFoundException("리뷰를 찾을 수 없습니다."));

            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 수정할 수 없습니다.");
            } else {
                if((principal.getName()).equals(review.getUser().getEmail())) {
                    review.setStar(request.getStar());
                    review.setContent(request.getContent());

                    Review updateReview = reviewRepository.saveAndFlush(review);

                    product.setReviewStarAvg(reviewRepository.getReviewStarAvg(prodId));
                    productRepository.saveAndFlush(product);

                    return CustomResponse.onSuccess(updateReview);
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 수정할 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 리뷰 삭제 */
    @Transactional
    public CustomResponse<Void> deleteReview(Principal principal, Long prodId, Long reviewId) {
        try {
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NotFoundException("리뷰를 찾을 수 없습니다."));

            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 삭제할 수 없습니다.");
            } else {
                if((principal.getName()).equals(review.getUser().getEmail())) {
                    setReviewCnt(product, product.getReviewCnt(), "delete");
                    reviewRepository.deleteById(reviewId);

                    product.setReviewStarAvg(reviewRepository.getReviewStarAvg(prodId));
                    productRepository.saveAndFlush(product);
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 삭제할 수 없습니다.");
                }
            }
            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 특정 사용자 리뷰 가져오기 */
    public CustomResponse<List<Review>> getUserReviewList(String userEmail) {
        try {
            List<Review> userReviewList = reviewRepository.findAllByUserEmail(userEmail);
            return CustomResponse.onSuccess(userReviewList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    public void setReviewCnt(Product product, int cnt, String calc) {
        if(product != null) {
            switch (calc) {
                case "add":
                    product.setReviewCnt(cnt + 1);
                    break;
                case "delete":
                    if(product.getReviewCnt() != 0) {
                        product.setReviewCnt(cnt - 1);
                    }
                    break;
            }
        }
    }
}
