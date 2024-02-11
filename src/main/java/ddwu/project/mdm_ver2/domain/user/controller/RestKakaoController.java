package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.cart.controller.CartApi;
import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.favorite.service.FavoriteService;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.review.service.ReviewService;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    private final UserService userService;

    @GetMapping("/kakao")
    public JwtToken login(@RequestParam String code, Model model) {

        String access_token = userService.getAccessToken(code);

        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);

        UserResponse userResponse = userService.checkKakaoUser(userInfo);

        JwtToken jwtToken = userService.setToken(userResponse);

        userService.addUser(userResponse);

        return jwtToken;
    }

    @GetMapping("/kakao/ios")
    public JwtToken loginIos(@RequestParam String access_token) {
        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);

        UserResponse userResponse = userService.checkKakaoUser(userInfo);
        userService.addUser(userResponse);

        return userService.setToken(userResponse);
    }

    @PostMapping("/reissue")
    public CustomResponse<JwtToken> reissue(HttpServletRequest request) {
        return userService.reissue(request);
    }

    @GetMapping("/kakao/logout")
    public CustomResponse<Void> logout(HttpServletRequest request) {
        return userService.logout(request.getHeader("Authorization"));
    }

//    @DeleteMapping("/kakao")
//    public void deleteUser(Principal principal) {
//        userService.deleteUser(principal.getName());
//    }
}
