package ddwu.project.mdm_ver2.domain.mypage.controller;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Tag(name = "MyPage", description = "마이페이지 API")
public interface MyPageApi {

    @Operation(summary = "닉네임 중복 확인")
    @ApiResponse(responseCode = "200", description = "닉네임 중복 여부 확인 성공")
    public boolean checkNickname(@PathVariable(value = "userNickname", required = true) String nickname, Model model);

    @Operation(summary = "사용자 찜 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 찜 리스트 불러오기 성공"),
            @ApiResponse(responseCode = "404", description = "찜 리스트가 없거나 사용자를 찾을 수 없음")
    })
    public CustomResponse<List<Favorite>> getUserFavorite(@Parameter(description = "현재 사용자 객체") Principal principal);

    @Operation(summary = "사용자 작성 리뷰 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 작성 리뷰 리스트 불러오기 성공"),
            @ApiResponse(responseCode = "404", description = "작성한 리뷰가 없거나 사용자를 찾을 수 없음")
    })
    public CustomResponse<List<Review>> getUserReview(@Parameter(description = "현재 사용자 객체") Principal principal);

}
