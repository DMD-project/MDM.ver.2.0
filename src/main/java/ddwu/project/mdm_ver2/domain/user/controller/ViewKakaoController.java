package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ViewKakaoController {

    @Autowired
    private UserService ks;

    @GetMapping("/login")
    public String reqLogin() {
        return "kakaoLogin"; //Figma(login)
    }

    /*
    @GetMapping("/kakao")
    public String login(@RequestParam String access_token, Model model) {

        System.out.println(access_token);

        System.out.println("in ViewKakaoController login");

//        String access_token = ks.getAccessToken(code);
        UserDTO userInfo = ks.getKakaoUserInfo(access_token);

        if (userInfo.getUserCode() < 0) {

            System.out.println(userInfo.getKakaoEmail());

            boolean userExist = ks.existUser(userInfo.getUserCode());

            if(!userExist) {
                System.out.println("user is not exist\nsaving ...");
//                session.setAttribute("newUser", userInfo);
                ks.addUser(userInfo);
                model.addAttribute("kakaoEmail", userInfo.getKakaoEmail());
                return "join"; //Figma(join)

            }
        }

        System.out.println("user is already exist !");
        return "redirect:/"; //Figma(Main uri)

    }*/

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
