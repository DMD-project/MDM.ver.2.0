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

//    public void deleteUser(Principal principal);
}