package ddwu.project.mdm_ver2.domain.product.controller.productAdmin;

import ddwu.project.mdm_ver2.domain.product.dto.ProductRequest;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin", description = "관리자 API")
public interface ProductAdminApi {

    @Operation(summary = "일반상품 등록", description = "상품을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품을 추가했습니다.", content = @Content(schema = @Schema(implementation = CustomResponse.class)))
    CustomResponse<Void> addProduct(@RequestBody ProductRequest request);

    @Operation(summary = "일반상품 수정", description = "상품을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품을 수정했습니다.", content = @Content(schema = @Schema(implementation = CustomResponse.class)))
    CustomResponse<Void> updateProduct(@RequestBody ProductRequest request,
                                       @Parameter(description = "일반상품 ID") @PathVariable("prodId") Long prodId);

    @Operation(summary = "일반상품 삭제", description = "상품을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 상품을 삭제했습니다.", content = @Content(schema = @Schema(implementation = CustomResponse.class)))
    CustomResponse<Void> deleteBag(@Parameter(description = "일반상품 ID") @PathVariable("prodId") Long prodId);
}