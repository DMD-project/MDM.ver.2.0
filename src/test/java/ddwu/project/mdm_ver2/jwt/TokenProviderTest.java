package ddwu.project.mdm_ver2.jwt;

import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;

@SpringBootTest
public class TokenProviderTest{
    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void 토큰_생성하기() {
        String token = jwtProvider.createAccessToken(3277908747L, "test");
        System.out.println(">>>>>>>>>>>>>> token = " + token);
    }

    @Test
    public void 토큰_검증하기() {
        String token = jwtProvider.createAccessToken(3277908747L, "test");
        System.out.println(jwtProvider.isValidate(token));

    }

    @Test
    public void jwt_usercode확인() {
        String token = "eyJ0eXBlIjoiYWNjZXNzIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyQ29kZSI6MzI3NzkwODc0NywiaWF0IjoxNzA2NDU5NzE0LCJleHAiOjE3MDY0NjE1MTR9.ajOHxP6V6gc_yYNUky9uKHt9XDD6Obwz0V9BY0v7YJI";
        System.out.println(jwtProvider.getUserId(token));
    }
}
