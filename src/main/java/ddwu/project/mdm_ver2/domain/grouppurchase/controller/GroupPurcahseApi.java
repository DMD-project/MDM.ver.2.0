package ddwu.project.mdm_ver2.domain.grouppurchase.controller;

import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchaseParticipant;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "GroupPurcahse", description = "공동구매 API")
public interface GroupPurcahseApi {

    @Operation(summary = "공동구매 목록 조회")
    @ApiResponse(responseCode = "200", description = "공동구매 목록 조회 성공")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<List<GroupPurchase>> getGroupPurchaseList();

    @Operation(summary = "공동구매 정렬")
    @ApiResponse(responseCode = "200", description = "공동구매 정렬 성공")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<List<GroupPurchase>> getSortedList(
            @Parameter(description = "정렬 방법", required = false, schema = @Schema(allowableValues = {"lowprice", "highprice", "newest"})) @RequestParam(name = "sortBy") String sortBy,
            @Parameter(description = "카테고리 코드", required = false, schema = @Schema(allowableValues = {"FUR", "FAB", "AD", "STO", "DEC", "LIT", "PLA"})) @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode);

    @Operation(summary = "특정 공동구매상품 조회")
    @ApiResponse(responseCode = "200", description = "특정 공동구매상품 조회 성공")
    @ApiResponse(responseCode = "404", description = "공동구매를 찾을 수 없음")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<List<GroupPurchaseParticipant>> getGroupPurchase(@PathVariable Long gpId);

    @Operation(summary = "공동구매 전체 개수 조회")
    @ApiResponse(responseCode = "200", description = "공동구매 전체 개수 조회 성공")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<Long> countAllGroupPurchases();

    @Operation(summary = "공동구매 참여")
    @ApiResponse(responseCode = "200", description = "공동구매 참여 성공")
    @ApiResponse(responseCode = "400", description = "이미 공동구매에 참여한 사용자입니다.")
    @ApiResponse(responseCode = "404", description = "공동구매를 찾을 수 없음")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<String> joinGroupPurchase(@PathVariable Long gpId, @PathVariable int purchasedQty, Principal principal);

    @Operation(summary = "공동구매 취소")
    @ApiResponse(responseCode = "200", description = "공동구매 취소 성공")
    @ApiResponse(responseCode = "400", description = "사용자가 공동구매에 참여하지 않았거나 취소 권한이 없습니다.")
    @ApiResponse(responseCode = "404", description = "공동구매를 찾을 수 없음")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<String> cancelGroupPurchase(@PathVariable Long gpId, Principal principal);

    @Operation(summary = "공동구매 검색")
    @ApiResponse(responseCode = "200", description = "공동구매 검색 성공")
    @ApiResponse(responseCode = "500", description = "서버 오류")
    CustomResponse<List<GroupPurchase>> searchGroupPurchase(@Parameter(description = "상품이름 검색", required = true) @RequestParam(name = "keyword") String keyword);

}
