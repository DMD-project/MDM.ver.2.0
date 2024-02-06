package ddwu.project.mdm_ver2.domain.product.controller;

import ddwu.project.mdm_ver2.domain.product.dto.ProductResponse;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "Product", description = "일반상품 API")
public interface ProductApi {
    @Operation(summary = "상품 목록 조회", description = "모든 상품 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    CustomResponse<List<Product>> getProductList();

    @Operation(summary = "상품 정렬", description = "지정된 기준으로 상품을 정렬합니다.")
    @ApiResponse(responseCode = "200", description = "상품 정렬 성공")
    CustomResponse<List<Product>> getSortedList(
            @Parameter(description = "정렬 방법", required = false, schema = @Schema(allowableValues = {"lowprice", "highprice", "newest"})) @RequestParam(name = "sortBy") String sortBy,
            @Parameter(description = "카테고리 코드", required = false, schema = @Schema(allowableValues = {"FUR", "FAB", "AD", "STO", "DEC", "LIT", "PLA"})) @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode);


    @Operation(summary = "상품 개별 조회", description = "특정 상품을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 조회 성공")
    CustomResponse<ProductResponse> getProduct(
            @Parameter(description = "사용자 이메일", required = true) Principal principal,
            @Parameter(description = "상품 ID", required = true) Long prodId);

    @Operation(summary = "상품 개수 조회", description = "상품의 총 개수를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "상품 개수 조회 성공")
    CustomResponse<Long> getProductCount(
            @Parameter(description = "카테고리 코드", required = false) String cateCode);

    @Operation(summary = "상품 검색", description = "지정된 키워드로 상품을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "상품 검색 성공")
    CustomResponse<List<Product>> searchProduct(
            @Parameter(description = "상품이름 검색", required = true) String keyword);
}
