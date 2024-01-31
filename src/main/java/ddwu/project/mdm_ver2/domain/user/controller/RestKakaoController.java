package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.user.entity.Role;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;

//@Controller
@RestController
@AllArgsConstructor
public class RestKakaoController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService ks;
    private JwtProvider jwtProvider;

    @GetMapping("/kakao/ios")
    public JwtToken loginIos(@RequestParam String access_token) {
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        UserResponse userResponse = ks.checkKakaoUser(userInfo);

        String jwt_access = jwtProvider.createAccessToken(userResponse.getId());
        String jwt_refresh = jwtProvider.createRefreshToken(userResponse.getId());

        ks.addUser(userResponse);

        return new JwtToken(jwt_access, jwt_refresh);
    }

    @GetMapping("/kakaoJoin/check/{userNickname}") // 중복확인 버튼 클릭
    public boolean checkNickname(@PathVariable(value="userNickname", required=true) String nickname, Model model) {
        log.info("nickname: {}", nickname);
        log.info("nickname 중복 여부: {}", ks.checkNicknameDup(nickname)); // 중복 -> true, 중복X -> false
        model.addAttribute("nicknameDup", ks.checkNicknameDup(nickname));
        return ks.checkNicknameDup(nickname);
    }

    @DeleteMapping("/kakao")
    public void deleteUser(Principal principal) {
        ks.deleteUser(principal.getName());
    }
}
