package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.cart.controller.CartApi;
import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.favorite.service.FavoriteService;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.review.service.ReviewService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

//@Controller
@RestController
@AllArgsConstructor
public class RestKakaoController implements UserApi {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final UserService userService;
    private final FavoriteService favoriteService;
    private final ReviewService reviewService;
    private final JwtProvider jwtProvider;

    @GetMapping("/kakao/ios")
    public JwtToken loginIos(@RequestParam String access_token) {
        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);

        UserResponse userResponse = userService.checkKakaoUser(userInfo);

        String jwt_access = jwtProvider.createAccessToken(userResponse.getId());
        String jwt_refresh = jwtProvider.createRefreshToken(userResponse.getId());

        System.out.println("jwt_access: " +jwt_access);

        userService.addUser(userResponse);

        return new JwtToken(jwt_access, jwt_refresh);
    }

    @GetMapping("/kakaoJoin/check/{userNickname}") // 중복확인 버튼 클릭
    public boolean checkNickname(@PathVariable(value="userNickname", required=true) String nickname, Model model) {
        log.info("nickname: {}", nickname);
        log.info("nickname 중복 여부: {}", userService.checkNicknameDup(nickname)); // 중복 -> true, 중복X -> false
        model.addAttribute("nicknameDup", userService.checkNicknameDup(nickname));
        return userService.checkNicknameDup(nickname);
    }

    @GetMapping("/mypage/favorite")
    public CustomResponse<List<Favorite>> getUserFavorite(Principal principal) {
        return favoriteService.getUserFavoriteList(principal.getName());
    }

    @GetMapping("/mypage/review")
    public CustomResponse<List<Review>> getUserReview(Principal principal) {
        return reviewService.getUserReviewList(principal.getName());
    }

    @DeleteMapping("/kakao")
    public void deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
    }
}
