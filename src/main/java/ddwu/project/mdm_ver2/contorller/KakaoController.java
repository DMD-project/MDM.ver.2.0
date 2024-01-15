package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.repository.UserRepository;
import ddwu.project.mdm_ver2.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
//@RestController
@AllArgsConstructor
public class KakaoController {

    @Autowired
    KakaoService ks;

    private UserRepository userRepository;

//    @Autowired
//    public KakaoController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @GetMapping("/login")
    public String reqLogin() {
        return "login";
    }


    @GetMapping("/kakao")
    public String login(@RequestParam String code, Model model, HttpSession session) {

        System.out.println(code);

//        System.out.println("in KakaoController login");

        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        if (userInfo.get("userCode") != null) {
            System.out.println(userInfo.get("kakaoEmail").toString());
            model.addAttribute("userCode", userInfo.get("userCode"));
            model.addAttribute("kakaoEmail", userInfo.get("kakaoEmail"));
            model.addAttribute("kakaoProfileImg", userInfo.get("kakaoProfileImg"));

            session.setAttribute("userCode", userInfo.get("userCode"));
        }

//        return "http://localhost:8080/success";

        if((boolean)userInfo.get("newUser")) {
            return "setNickname";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/kakaoJoin")
    public String submit(@RequestParam(value="userNickname", required=false) String nickname, Model model, HttpSession session) {

        System.out.println("KakaoController>submit: " +nickname);
        System.out.println("KakaoController>submit: " +session.getAttribute("userCode"));

        session.setAttribute("nickname", nickname);
        model.addAttribute("session", session);
        model.addAttribute("nickname", nickname);

        ks.setUserNickname((long) session.getAttribute("userCode"), nickname);

        return "redirect:/";
    }

    public void logout() {
//       session.invalidate();
    }

    @GetMapping("/deleteKakao")
    public String deleteUser(HttpSession session) {
        ks.deleteUser((Long) session.getAttribute("userCode"));
        session.invalidate();
        return "redirect:/";
    }
}