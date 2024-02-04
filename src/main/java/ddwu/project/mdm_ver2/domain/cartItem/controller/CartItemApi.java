package ddwu.project.mdm_ver2.domain.cartItem.controller;

import ddwu.project.mdm_ver2.domain.cartItem.entity.Items;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "CartItem", description = "장바구니 품목 API")
public interface CartItemApi {

    @Operation(summary = "장바구니 품목 추가")
    @ApiResponse(responseCode = "200", description = "장바구니 품목 추가 성공")
    CustomResponse<Items> addItemToCart(@Parameter(description = "현재 사용자의 Principal 객체") Principal principal,
                                        @PathVariable("prodId") long prodId,
                                        @PathVariable("count") int count);

    @Operation(summary = "장바구니 품목 증가(1개씩)")
    @ApiResponse(responseCode = "200", description = "장바구니 품목 증가 성공")
    CustomResponse<Items> increaseItem(@PathVariable("itemsId") long itemsId);

    @Operation(summary = "장바구니 품목 감소(1개씩)")
    @ApiResponse(responseCode = "200", description = "장바구니 품목 감소 성공")
    CustomResponse<Items> decreaseItem(@PathVariable("itemsId") long itemsId);

    @Operation(summary = "장바구니 선택한 품목들 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 품목 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "장바구니 품목을 찾을 수 없음")
    })
    CustomResponse<Void> deleteCartItems(@RequestParam(name = "itemsId") List<Long> itemsId);

}
