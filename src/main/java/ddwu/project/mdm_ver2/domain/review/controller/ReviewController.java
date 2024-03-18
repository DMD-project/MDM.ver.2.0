package ddwu.project.mdm_ver2.domain.review.controller;

import ddwu.project.mdm_ver2.domain.review.dto.ReviewRequest;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.review.service.ReviewService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/review/{prodId}")
public class ReviewController implements ReviewApi{

    private ReviewService reviewService;

    /* 리뷰 정렬 */
    @GetMapping("sort")
    public CustomResponse<List<Review>> getSortedList(@PathVariable("prodId") Long prodId,
                                                      @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy) {
        return reviewService.sortReview(prodId, sortBy);
    }

    /* 리뷰 등록 */
    @PostMapping("/add")
    public CustomResponse<Void> addReview(Principal principal, @PathVariable("prodId") Long prodId, @RequestBody ReviewRequest request) {
        return reviewService.addReview(principal.getName(), prodId, request);
    }

    /* 리뷰 수정 */
    @PostMapping("/update/{reviewId}")
    public CustomResponse<Void> updateReview(Principal principal, @PathVariable("prodId") Long prodId,
                                               @PathVariable("reviewId") Long reviewId,
                                               @RequestBody ReviewRequest request) {
        return reviewService.updateReview(principal, prodId, reviewId, request);
    }

    /* 리뷰 삭제 */
    @DeleteMapping("/delete/{reviewId}")
    public CustomResponse<Void> deleteReview(Principal principal, @PathVariable("prodId") Long prodId, @PathVariable("reviewId") Long reviewId) {
        return reviewService.deleteReview(principal, prodId, reviewId);
    }
}