package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.dto.UserDTO;
import ddwu.project.mdm_ver2.jwt.JwtProvider;
import ddwu.project.mdm_ver2.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

//@Controller
@RestController
@AllArgsConstructor
public class RestKakaoController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private KakaoService ks;
    private JwtProvider jwtProvider;

    @GetMapping("/kakao")
    public boolean login(@RequestParam String code) {
        log.info("code: {}", code);
        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);
        UserDTO newUser = new UserDTO((long) userInfo.get("userCode"), null, userInfo.get("kakaoEmail").toString(), userInfo.get("kakaoProfileImg").toString());
        return ks.checkKakaoUser(newUser);
    }

    @GetMapping("/kakao/ios")
    public boolean loginIos(@RequestParam String access_token) {
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);
        UserDTO newUser = new UserDTO((long) userInfo.get("userCode"), null, userInfo.get("kakaoEmail").toString(), userInfo.get("kakaoProfileImg").toString());
        return ks.checkKakaoUser(newUser);
    }

    /* set nickname for NEW user */
    @GetMapping("/kakaoJoin")
    public UserDTO create(@RequestParam(value="kakaoEmail", required=false) String kakaoEmail,
                          @RequestParam(value="userNickname", required=false) String nickname) {

        log.info("new user kakaoEmail: {}", kakaoEmail);
        log.info("new user nickname: {}", nickname);

        UserDTO newUser = ks.setUserNickname(kakaoEmail, nickname).toDTO();

        jwtProvider.createAccessToken(newUser.getUserCode());
        jwtProvider.createRefreshToken(newUser.getUserCode());

        return newUser;
    }

    @GetMapping("/kakaoJoin/check/{userNickname}") // 중복확인 버튼 클릭
    public boolean checkNickname(@PathVariable(value="userNickname", required=true) String nickname, Model model) {
        System.out.println(nickname);
        System.out.println(ks.checkNicknameDup(nickname)); // 중복 -> true, 중복X -> false
        model.addAttribute("nicknameDup", ks.checkNicknameDup(nickname));
        return ks.checkNicknameDup(nickname);
    }

    @DeleteMapping("/kakao/{userCode}")
    public void deleteUser(@RequestParam(value="userCode", required=true) long userCode) {
        ks.deleteUser(userCode);
    }
}