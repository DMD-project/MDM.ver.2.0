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

import java.util.HashMap;

//@Controller
@RestController
@AllArgsConstructor
public class RestKakaoController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService ks;
    private JwtProvider jwtProvider;

    /*
    @GetMapping("/kakao")
    public JwtToken login(@RequestParam String code) {
        log.info("code: {}", code);

        String access_token = ks.getAccessToken(code);
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        UserResponse userResponse = ks.checkKakaoUser(userInfo);

        String jwt_access = jwtProvider.createAccessToken(userResponse.getUserCode());
        String jwt_refresh = jwtProvider.createRefreshToken(userResponse.getUserCode());

        ks.addUser(userResponse);

        return new JwtToken(jwt_access, jwt_refresh);
    }*/

    @GetMapping("/kakao/ios")
    public JwtToken loginIos(@RequestParam String access_token) {
        HashMap<String, Object> userInfo = ks.getKakaoUserInfo(access_token);

        UserResponse userResponse = ks.checkKakaoUser(userInfo);

        String jwt_access = jwtProvider.createAccessToken(userResponse.getUserCode());
        String jwt_refresh = jwtProvider.createRefreshToken(userResponse.getUserCode());

        ks.addUser(userResponse);

        return new JwtToken(jwt_access, jwt_refresh);
    }

    /* set nickname for NEW user */
//    @GetMapping("/kakaoJoin")
//    public UserDTO create(@RequestParam(value="kakaoEmail", required=false) String kakaoEmail,
//                          @RequestParam(value="userNickname", required=false) String nickname) {
//
//        log.info("new user kakaoEmail: {}", kakaoEmail);
//        log.info("new user nickname: {}", nickname);
//
//        UserDTO newUser = ks.setUserNickname(kakaoEmail, nickname).toDTO();
//
//        jwtProvider.createAccessToken(newUser.getUserCode());
//        jwtProvider.createRefreshToken(newUser.getUserCode());
//
//        return newUser;
//    }

    @GetMapping("/kakaoJoin/check/{userNickname}") // 중복확인 버튼 클릭
    public boolean checkNickname(@PathVariable(value="userNickname", required=true) String nickname, Model model) {
        System.out.println(nickname);
        System.out.println(ks.checkNicknameDup(nickname)); // 중복 -> true, 중복X -> false
        model.addAttribute("nicknameDup", ks.checkNicknameDup(nickname));
        return ks.checkNicknameDup(nickname);
    }

    @DeleteMapping("/kakao/{kakaoEmail}")
    public void deleteUser(@RequestParam(value="userCode", required=true) String kakaoEmail) {
        ks.deleteUser(kakaoEmail);
    }
}
