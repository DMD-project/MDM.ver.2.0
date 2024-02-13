package ddwu.project.mdm_ver2.domain.secondhand.controller.shBid;

import ddwu.project.mdm_ver2.domain.secondhand.dto.shBid.SecondHandBidDto;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
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

@Tag(name = "SecondHandBid", description = "중고 거래 요청 API")
public interface SecondHandBidApi {

    @Operation(summary = "중고 거래 요청 정렬")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 조회 및 정렬 성공"),
            @ApiResponse(responseCode = "404", description = "요청이 없거나 찾을 수 없음")
    })
    public CustomResponse<List<SecondHandBid>> getSortedList(@Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId,
                                                             @Parameter(description = "요청 정렬 방식") @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy);

    @Operation(summary = "중고 거래 요청 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 작성 성공"),
            @ApiResponse(responseCode = "500", description = "요청 작성 실패")
    })
    public CustomResponse<SecondHandBid> addShBid(@Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId,
                                                  @Parameter(description = "새 요청 내용") @RequestBody SecondHandBidDto request);

    @Operation(summary = "중고 거래 요청 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 요청을 찾을 수 없음"),
            @ApiResponse(responseCode = "405", description = "해당 요청을 수정할 수 없는 사용자"),
            @ApiResponse(responseCode = "500", description = "요청 수정 실패")
    })
    public CustomResponse<SecondHandBid> updateShBid(@Parameter(description = "현재 사용자 객체") Principal principal,
                                                     @Parameter(description = "현재 요청 아이디") @PathVariable("shBidId") Long shBidId,
                                                     @Parameter(description = "요청 수정 내용") @RequestBody SecondHandBidDto request);

    @Operation(summary = "중고 거래 요청 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 요청을 찾을 수 없음"),
            @ApiResponse(responseCode = "405", description = "해당 요청을 삭제할 수 없는 사용자"),
            @ApiResponse(responseCode = "500", description = "요청 삭제 실패")
    })
    public CustomResponse<Void> deleteShBid(@Parameter(description = "현재 사용자 객체") Principal principal,
                                            @Parameter(description = "현재 중고 거래 상품 아이디") @PathVariable("shId") Long shId,
                                            @Parameter(description = "현재 요청 아이디") @PathVariable("shBidId") Long shBidId);
    }
