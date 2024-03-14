package ddwu.project.mdm_ver2.domain.order.controller;

import ddwu.project.mdm_ver2.domain.order.dto.OrderAddrRequest;
import ddwu.project.mdm_ver2.domain.order.dto.OrderCartRequest;
import ddwu.project.mdm_ver2.domain.order.dto.OrderDto;
import ddwu.project.mdm_ver2.domain.order.entity.Order;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Tag(name = "Order", description = "주문 API")
public interface OrderApi {

    @Operation(summary = "주문 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "해당 주문이 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    CustomResponse<Void> updateOrder(@Parameter(description = "주문 ID") @PathVariable("orderId") long orderId,
                                     @Parameter(description = "수정된 주문 정보") @RequestBody OrderAddrRequest updatedOrder);

    @Operation(summary = "주문 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 취소 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    CustomResponse<Void> cancelOrder(@Parameter(description = "주문 ID") @PathVariable("orderId") long orderId);

    @Operation(summary = "주문 상세 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    CustomResponse<Order> getOrderDetail(@Parameter(description = "주문 ID") @PathVariable("orderId") long orderId);

    @Operation(summary = "장바구니 상품 구매")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장바구니 항목 구매 추가 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    CustomResponse<Void> purchaseItemsFromCart(@Parameter(description = "현재 사용자 정보") Principal principal,
                                               @Parameter(description = "주문 정보") @RequestBody OrderCartRequest cartRequest);

    @Operation(summary = "일반상품 바로 구매")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반상품 바로 구매 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")})
    CustomResponse<Void> purchaseItems(@Parameter(description = "현재 사용자 정보") Principal principal,
                                       @Parameter(description = "상품 ID") @PathVariable Long productId,
                                       @Parameter(description = "구매 수량") @PathVariable int purchasedQty,
                                       @Parameter(description = "주문 정보") @RequestBody OrderDto orderDto);
}
