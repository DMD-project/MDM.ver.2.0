package ddwu.project.mdm_ver2.global.mapping.controller;

import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@AllArgsConstructor
public class MappingController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String reqLogin() {
        return "login";
    }

    @GetMapping("/kakao")
    public String login(@RequestParam String code, Model model, HttpServletResponse response) {

        String access_token = userService.getAccessToken(code);

        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);

        UserResponse userResponse = userService.checkKakaoUser(userInfo);

        JwtToken jwtToken = userService.setToken(userResponse, access_token);

        Cookie accessCookie = new Cookie("access_token", jwtToken.getAccess_token());
        accessCookie.setMaxAge(30 * 60); // 30분
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", jwtToken.getRefresh_token());
        refreshCookie.setMaxAge(14 * 24 * 60 * 60); // 2주
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        userService.addUser(userResponse);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logut";
    }

    @GetMapping("/product")
    public String product() {
        return "product";
    }

    @GetMapping("/gp")
    public String groupPurchase() {
        return "grouppurchase";
    }

    @GetMapping("/secondhand")
    public String secondHand() {
        return "secondhand";
    }
}
