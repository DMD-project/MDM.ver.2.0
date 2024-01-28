package ddwu.project.mdm_ver2.jwt;

import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenProviderTest{
    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void 토큰_생성하기() {
        String token = jwtProvider.createAccessToken(3290111211L);
        System.out.println(">>>>>>>>>>>>>> token = " + token);
    }

    @Test
    public void 토큰_검증하기() {
        String token = jwtProvider.createAccessToken(3290111211L);
        System.out.println(jwtProvider.isValidate(token));

    }

    @Test
    public void jwt_usercode확인() {
        String token = "eyJ0eXBlIjoiYWNjZXNzIiwiYWxnIjoiSFMyNTYifQ.eyJrYWthb190b2tlbiI6InBnQ2JnWFpKeGdrYVpEWEVKVFFWeVo2WnNNNTRTdDFUaE9vS0t3MGZBQUFCalZERzdUcGI5UG1yNWVnX1pBIiwiaWF0IjoxNzA2NDU3MjI4LCJleHAiOjE3MDY0NTkwMjh9.YvSMZzdE3APQEJTdGZTG1rgOTJ9qYpDeownH8AXAc0E";
        System.out.println(jwtProvider.getUserCode(token));
    }
}
