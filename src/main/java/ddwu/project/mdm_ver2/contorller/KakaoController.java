package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class KakaoController {

    @Autowired
    KakaoService ks;

    @GetMapping("/login")
    public String reqLogin() {
        return "login";
    }


    @GetMapping("/kakao")
    public String login(@RequestParam String code, Model model) {

//        System.out.println(code);

//        System.out.println("in KakaoController login");

        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        if (userInfo.get("userCode") != null) {
            System.out.println(userInfo.get("kakaoEmail").toString());
            model.addAttribute("userCode", userInfo.get("userCode"));
            model.addAttribute("kakaoEmail", userInfo.get("kakaoEmail"));
            model.addAttribute("kakaoProfileImg", userInfo.get("kakaoProfileImg"));
        }

//        return "http://localhost:8080/success";

        if((boolean)userInfo.get("newUser")) {
            return "setNickname";
        } else {
            return "redirect:/";
        }

    }

    @PostMapping("/kakaojoin")
    public String submit(@RequestParam(value="userNickname", required=false) String nickname) {

        System.out.println("KakaoController>submit: " +nickname);

        return "redirect:/";
    }
}