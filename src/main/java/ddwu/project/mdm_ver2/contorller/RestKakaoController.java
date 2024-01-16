package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@Controller
@RestController
@AllArgsConstructor
public class RestKakaoController {

    @Autowired
    private KakaoService ks;

    @PostMapping("/kakaoJoin") // set nickname for new user
    public User create(@RequestParam(value="userNickname", required=false) String nickname,
                       HttpSession session) {

        System.out.println("KakaoController>submit: " +nickname);

        User newUser = (User) session.getAttribute("newUser");

        newUser.setUserNickname(nickname);
//        System.out.println(ks.checkNicknameDup(nickname));
        return ks.addUser(newUser);
    }

    @GetMapping("/kakaoJoin/check/{userNickname}") // 중복확인 버튼 클릭
    public boolean checkNickname(@PathVariable(value="userNickname", required=true) String nickname, Model model) {
        System.out.println(nickname);
        System.out.println(ks.checkNicknameDup(nickname)); // 중복 -> true, 중복X -> false
        model.addAttribute("nicknameDup", ks.checkNicknameDup(nickname));
        return ks.checkNicknameDup(nickname);
    }

    public void logout() {
//       session.invalidate();
    }

    @DeleteMapping("/kakao/{userCode}")
    public void deleteUser(@RequestParam(value="userCode", required=true) long userCode) {
        ks.deleteUser(userCode);
    }
}