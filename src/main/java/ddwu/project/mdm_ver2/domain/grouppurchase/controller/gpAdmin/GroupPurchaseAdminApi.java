package ddwu.project.mdm_ver2.domain.grouppurchase.controller.gpAdmin;

import ddwu.project.mdm_ver2.domain.grouppurchase.dto.GroupPurchaseRequest;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin", description = "관리자 API")
public interface GroupPurchaseAdminApi {

    @Operation(summary = "공동구매 등록")
    @ApiResponse(responseCode = "200", description = "공동구매 등록 성공")
    CustomResponse<Void> addGroupPurchase(@RequestBody GroupPurchaseRequest request);

    @Operation(summary = "공동구매 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공동구매 수정 성공"),
            @ApiResponse(responseCode = "404", description = "공동구매를 찾을 수 없음")
    })
    CustomResponse<Void> updateGroupPurchase(@Parameter(description = "공동구매 ID") @PathVariable Long gpId,
                                             @RequestBody GroupPurchaseRequest request);

    @Operation(summary = "공동구매 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공동구매 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "공동구매를 찾을 수 없음")
    })
    CustomResponse<Void> deleteGroupPurchase(@Parameter(description = "공동구매 ID") @PathVariable Long gpId);
}