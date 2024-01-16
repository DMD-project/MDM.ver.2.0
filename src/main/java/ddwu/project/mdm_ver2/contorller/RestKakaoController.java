package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.service.KakaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@AllArgsConstructor
public class RestKakaoController {

    @Autowired
    private KakaoService ks;

    @PostMapping("/kakaoJoin") // set nickname for new user
    public User login(@RequestParam(value="userNickname", required=false) String nickname,
                      @RequestParam(value="email", required=false) String email) {

        System.out.println("KakaoController>submit: " +nickname);

        ks.getUser(email);

        return ks.setUserNickname(email, nickname);
    }

    public void logout() {
//       session.invalidate();
    }

    @DeleteMapping("/kakao/{id}")
    public void deleteUser(@RequestParam(value="userCode", required=true) long userCode) {
        ks.deleteUser(userCode);
    }
}