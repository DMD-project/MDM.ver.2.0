package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.service.KakaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@AllArgsConstructor
public class ViewKakaoController {

    @Autowired
    private KakaoService ks;

    @GetMapping("/login")
    public String reqLogin() {
        return "login"; //Figma(login)
    }

    @GetMapping("/kakao")
    public String login(@RequestParam String code, Model model) {

        System.out.println(code);

        System.out.println("in ViewKakaoController login");

        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        if (userInfo.get("userCode") != null) {
            System.out.println(userInfo.get("kakaoEmail").toString());

            boolean userExist = ks.existUser((long) userInfo.get("userCode"));

            if(!userExist) {
                System.out.println("user is not exist\nsaving ...");
                ks.addUser(new User((long) userInfo.get("userCode"), userInfo.get("kakaoEmail").toString(), userInfo.get("kakaoProfileImg").toString()));
                model.addAttribute("kakaoEmail", userInfo.get("kakaoEmail").toString());
                return "setNickname"; //Figma(join)

            }
        }

        System.out.println("user is already exist !");
        return "redirect:/"; //Figma(Main uri)

    }

    @GetMapping("/kakaoJoin")
    public String submit() {
        return "redirect:/"; //Figma(Main uri)
    }
}
