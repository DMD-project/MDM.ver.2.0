package ddwu.project.mdm_ver2.domain.review.controller;

import ddwu.project.mdm_ver2.domain.review.dto.ReviewRequest;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "Review", description = "리뷰 API")
public interface ReviewApi {

    @Operation(summary = "상품 리뷰 정렬")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 리뷰 조회 및 정렬 성공"),
            @ApiResponse(responseCode = "404", description = "상품 리뷰가 없거나 찾을 수 없음")
    })
    public CustomResponse<List<Review>> getSortedList(@Parameter(description = "현재 상품 아이디") @PathVariable("prodId") Long prodId,
                                                      @Parameter(description = "리뷰 정렬 방식") @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy);

    @Operation(summary = "상품 리뷰 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 작성 성공"),
            @ApiResponse(responseCode = "500", description = "리뷰 작성 실패")
    })
    public CustomResponse<Review> addReview(@Parameter(description = "현재 사용자 객체") Principal principal,
                                            @Parameter(description = "현재 상품 아이디") @PathVariable("prodId") Long prodId,
                                            @Parameter(description = "새 리뷰 작성 내용") @RequestBody ReviewRequest request);

    @Operation(summary = "상품 리뷰 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 리뷰를 찾을 수 없음"),
            @ApiResponse(responseCode = "405", description = "해당 리뷰를 수정할 수 없는 사용자"),
            @ApiResponse(responseCode = "500", description = "리뷰 수정 실패")
    })
    public CustomResponse<Review> updateReview(@Parameter(description = "현재 사용자 객체") Principal principal,
                                               @Parameter(description = "현재 상품 아이디") @PathVariable("prodId") Long prodId,
                                               @Parameter(description = "현재 상품의 리뷰 아이디") @PathVariable("reviewId") Long reviewId,
                                               @Parameter(description = "리뷰 수정 내용") @RequestBody ReviewRequest request);

    @Operation(summary = "상품 리뷰 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 리뷰를 찾을 수 없음"),
            @ApiResponse(responseCode = "405", description = "해당 리뷰를 삭제할 수 없는 사용자"),
            @ApiResponse(responseCode = "500", description = "리뷰 삭제 실패")
    })
    public CustomResponse<Void> deleteReview(@Parameter(description = "현재 사용자 객체") Principal principal,
                                             @Parameter(description = "현재 상품 아이디") @PathVariable("prodId") Long prodId,
                                             @Parameter(description = "현재 리뷰 아이디") @PathVariable("reviewId") Long reviewId);
    }
