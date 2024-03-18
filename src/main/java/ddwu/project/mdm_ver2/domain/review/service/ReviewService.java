package ddwu.project.mdm_ver2.domain.review.service;

import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.domain.order.entity.Order;
import ddwu.project.mdm_ver2.domain.order.service.OrderService;
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
    private final OrderService orderService;

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
                case "highstar":
                    sortedReviewList = reviewRepository.findAllByProductIdOrderByStarDesc(prodId);
                    break;
                default:
                    sortedReviewList = reviewRepository.findAllByProductId(prodId);
                    break;
            }

            return CustomResponse.onSuccess(sortedReviewList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    public CustomResponse<Void> addReview(String userEmail, Long prodId, ReviewRequest request) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            boolean reviewState = false;

            List<Order> orderList = orderService.getOrderByUser(userEmail).getContent();
            for (Order order : orderList) {
                List<Items> items = order.getCartItems();
                for (Items item : items) {
                    Long orderProdId = item.getId();
                    if (orderProdId.equals(prodId)) {
                        reviewState = true;
                    }
                }
            }

            if (reviewState) {
                Review review = Review.builder()
                        .user(user)
                        .product(product)
                        .star(request.getStar())
                        .date(request.getDate())
                        .content(request.getContent())
                        .build();
                reviewRepository.saveAndFlush(review);

                product.setReviewCnt(reviewRepository.countByProductId(prodId));
                product.setReviewStarAvg(reviewRepository.getReviewStarAvg(prodId));
                productRepository.saveAndFlush(product);

                return CustomResponse.onSuccess("리뷰가 등록되었습니다.");
            } else {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "상품을 구매한 사용자만 작성이 가능합니다.");
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public CustomResponse<Void> updateReview(Principal principal, Long prodId, Long reviewId, ReviewRequest request) {
        try {
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NotFoundException("리뷰를 찾을 수 없습니다."));

            if (principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 수정할 수 없습니다.");
            } else {
                if ((principal.getName()).equals(review.getUser().getEmail())) {
                    review.setStar(request.getStar());
                    review.setContent(request.getContent());
                    reviewRepository.saveAndFlush(review);

                    product.setReviewStarAvg(reviewRepository.getReviewStarAvg(prodId));
                    productRepository.saveAndFlush(product);

                    return CustomResponse.onSuccess("리뷰가 수정되었습니다.");
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 수정할 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @Transactional
    public CustomResponse<Void> deleteReview(Principal principal, Long prodId, Long reviewId) {
        try {
            Product product = productRepository.findById(prodId)
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NotFoundException("리뷰를 찾을 수 없습니다."));

            if (principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "리뷰를 삭제할 수 없습니다.");
            } else {
                if ((principal.getName()).equals(review.getUser().getEmail())) {
                    reviewRepository.deleteById(reviewId);
                    product.setReviewCnt(reviewRepository.countByProductId(prodId));

                    if (product.getReviewCnt() == 0) {
                        product.setReviewStarAvg(0);
                    } else {
                        product.setReviewStarAvg(reviewRepository.getReviewStarAvg(prodId));
                    }

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

    public CustomResponse<List<Review>> getUserReviewList(String userEmail) {
        try {
            List<Review> userReviewList = reviewRepository.findAllByUserEmail(userEmail);
            return CustomResponse.onSuccess(userReviewList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    public void clearUserData(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<Review> reviewList = reviewRepository.findAllByUserEmail(user.getEmail());
        for (Review review : reviewList) {
            Product product = productRepository.findById(review.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

            reviewRepository.deleteById(review.getId());
            product.setReviewCnt(reviewRepository.countByProductId(product.getId()));
            product.setReviewStarAvg(reviewRepository.getReviewStarAvg(product.getId()));
        }
    }
}