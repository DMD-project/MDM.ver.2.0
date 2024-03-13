package ddwu.project.mdm_ver2.domain.favorite.controller;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Tag(name = "Favorite", description = "찜 API")
public interface FavoriteApi {

    @Operation(summary = "찜 상태 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찜 상태 변경 성공"),
            @ApiResponse(responseCode = "500", description = "찜 상태를 변경할 수 없음")
    })
    public CustomResponse<Favorite> changeFavoriteState(@Parameter(description = "현재 사용자 객체") Principal principal,
                                                        @Parameter(description = "일반 상품/중고 거래/공동 구매 구분") @PathVariable(value = "favType", required = true) String favType,
                                                        @Parameter(description = "일반 상품/중고 거래/공동 구매 아이디") @PathVariable(value = "typeId", required = true) long typeId,
                                                        @Parameter(description = "현재 찜 상태") @PathVariable(value = "favState", required = true) Character favState);
}