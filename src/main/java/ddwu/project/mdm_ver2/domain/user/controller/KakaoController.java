package ddwu.project.mdm_ver2.domain.user.controller;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class KakaoController implements UserApi {

    private final UserService userService;

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

    @PostMapping("/kakao/logout")
    public CustomResponse<Void> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @DeleteMapping("/kakao/withdrawal")
    public CustomResponse<Void> withdrawal(HttpServletRequest request) {
        return userService.deleteUser(request);
    }
}