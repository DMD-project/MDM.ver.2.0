package ddwu.project.mdm_ver2.domain.user.controller;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class RestKakaoController implements UserApi {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

//    @GetMapping("/kakao")
//    public JwtToken login(@RequestParam String code, Model model, HttpServletResponse response) {
//
//        String access_token = userService.getAccessToken(code);
//
//        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);
//
//        UserResponse userResponse = userService.checkKakaoUser(userInfo);
//
//        JwtToken jwtToken = userService.setToken(userResponse, access_token);
//
//        Cookie accessCookie = new Cookie("access_token", jwtToken.getAccess_token());
//        accessCookie.setMaxAge(30 * 60); // 30분
//        accessCookie.setPath("/");
//        response.addCookie(accessCookie);
//
//        Cookie refreshCookie = new Cookie("refresh_token", jwtToken.getRefresh_token());
//        refreshCookie.setMaxAge(14 * 24 * 60 * 60); // 2주
//        refreshCookie.setPath("/");
//        response.addCookie(refreshCookie);
//
//        userService.addUser(userResponse);
//        return jwtToken;
//    }

    @GetMapping("/kakao/ios")
    public JwtToken loginIos(@RequestParam String access_token) {
        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);

        UserResponse userResponse = userService.checkKakaoUser(userInfo);
        userService.addUser(userResponse);

        return userService.setToken(userResponse, access_token);
    }

    @PostMapping("/reissue")
    public CustomResponse<JwtToken> reissue(HttpServletRequest request) {
        return userService.reissue(request);
    }

    @GetMapping("/kakao/logout")
    public CustomResponse<Void> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @DeleteMapping("/kakao/withdrawal")
    public CustomResponse<Void> withdrawal(HttpServletRequest request) {
        return userService.deleteUser(request);
    }
}
