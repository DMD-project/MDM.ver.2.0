package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Tag(name = "User", description = "사용자 API")
public interface UserApi {

    @Operation(summary = "카카오 로그인(access token 받기, 사용자 정보 받아 오기, jwt token 생성")
    @ApiResponse(responseCode = "200", description = "받아온 사용자 정보로 jwt token 생성")
    public JwtToken login(@Parameter(description = "카카오 인가 코드") @RequestParam String code, Model model);

    @Operation(summary = "카카오 로그인(사용자 정보 받아오기, jwt token 생성")
    @ApiResponse(responseCode = "200", description = "받아온 사용자 정보로 jwt token 생성")
    public JwtToken loginIos(@Parameter(description = "카카오 access token") @RequestParam String access_token);

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

//    public void deleteUser(Principal principal);
}