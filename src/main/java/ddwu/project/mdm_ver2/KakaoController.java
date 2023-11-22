package ddwu.project.mdm_ver2;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class KakaoController {

    @Autowired
    KakaoService ks;

    @ResponseBody
    @GetMapping("/kakao")
//    @RequestMapping("/kakao")
    public String login(@RequestParam String code, HttpSession session) {

        System.out.println(code);
//
        System.out.println("in KakaoController login");

        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        if (userInfo.get("userID") != null) {
            session.setAttribute("userID", userInfo.get("userID"));
            session.setAttribute("profileIMG", userInfo.get("profileIMG"));
        }

//        return "http://localhost:8080/success";
        return "stateService";
    }
}