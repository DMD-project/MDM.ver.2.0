package ddwu.project.mdm_ver2.domain.secondhand.controller;

import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandRequest;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandResponse;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
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

@Tag(name = "SecondHand", description = "중고 거래 API")
public interface SecondHandApi {

    @Operation(summary = "전체 중고 거래 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 상품 조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품이 없거나 찾을 수 없음")
    })
    public CustomResponse<List<SecondHand>> getSecondHandList();

    @Operation(summary = "전체 중고 거래 상품 정렬")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 상품 정렬 성공"),
            @ApiResponse(responseCode = "404", description = "상품이 없거나 찾을 수 없음")
    })
    public CustomResponse<List<SecondHand>> getSortedList(@Parameter(description = "중고 거래 정렬 방식") @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
                                                          @Parameter(description = "카테고리 코드") @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode);

    @Operation(summary = "중고 거래 상품 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품이 없거나 찾을 수 없음")
    })
    public CustomResponse<SecondHandResponse> getSeconHand(@Parameter(description = "현재 사용자 객체") Principal principal,
                                                           @Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId);

    @Operation(summary = "중고 거래 상품 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 추가 성공"),
            @ApiResponse(responseCode = "500", description = "상품 추가 실패")
    })
    public CustomResponse<SecondHand> addSecondHand(@Parameter(description = "새 중고 거래 상품 내용") @RequestBody SecondHandRequest request);

    public List<SecondHand> searchSecondHand(@Parameter(description = "검색 키워드") @PathVariable("keyword") String keyword);

    @Operation(summary = "중고 거래 상품 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "500", description = "상품 수정 실패")
    })
    public CustomResponse<SecondHand> updateSecondHand(@Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId,
                                                       @Parameter(description = "중고 거래 상품 수정 내용") @RequestBody SecondHandRequest request);


    @Operation(summary = "현재 사용자의 중고 거래 상품 찜 상태 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찜 상태 변경 성공"),
            @ApiResponse(responseCode = "500", description = "찜 상태 변경 실패")
    })
    public CustomResponse<SecondHand> updateSecondHandState(@Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId,
                                                            @Parameter(description = "현재 사용자의 중고 거래 상품 찜 상태") @PathVariable("state") char state);

    @Operation(summary = "중고 거래 상품 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "500", description = "상품 삭제 실패")
    })
    public CustomResponse<Void> deleteSecondHand(@Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId);
}
