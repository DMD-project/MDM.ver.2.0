package ddwu.project.mdm_ver2.domain.cart.controller;

import ddwu.project.mdm_ver2.domain.cart.entity.Cart;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "Cart", description = "장바구니 API")
public interface CartApi {

    @Operation(summary = "사용자 장바구니 조회")
    @ApiResponse(responseCode = "200", description = "사용자 장바구니 조회 성공")
    public CustomResponse<Cart> getCartByUser(@Parameter(description = "현재 사용자의 Principal 객체") Principal principal);

    @Operation(summary = "장바구니 비우기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 비우기 성공"),
            @ApiResponse(responseCode = "404", description = "장바구니가 비어있거나 사용자를 찾을 수 없음")
    })
    public CustomResponse<Void> clearCart(@Parameter(description = "현재 사용자의 Principal 객체") Principal principal);
}