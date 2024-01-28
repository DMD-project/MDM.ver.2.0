package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@AllArgsConstructor
public class ViewKakaoController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService ks;
    private JwtProvider jwtProvider;

    @GetMapping("/login")
    public String reqLogin() {
        return "login"; //Figma(login)
    }


    @GetMapping("/kakao")
    public String login(@RequestParam String code, Model model) {

        log.info("code: {}", code);

        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        UserResponse userResponse = ks.checkKakaoUser(userInfo);

        String jwt_access = jwtProvider.createAccessToken(userResponse.getUserCode());
        String jwt_refresh = jwtProvider.createRefreshToken(userResponse.getUserCode());

        ks.addUser(userResponse);
        model.addAttribute("jwt_access", jwt_access);
//        model.addAttribute("jwt_refresh", jwt_refresh);
        return "redirect:/";

    }

//    @GetMapping("/kakaoJoin")
//    public String submit() {
//        return "redirect:/"; //Figma(Main uri)
//    }

    @GetMapping("/logout")
    public String logout() {
//       ks.logout(session.getAttribute("access_token").toString());
//       session.invalidate();
        return "redirect:/";
    }
}
