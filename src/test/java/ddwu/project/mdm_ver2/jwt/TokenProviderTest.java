package ddwu.project.mdm_ver2.jwt;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenProviderTest{
    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void 토큰_생성하기() {
        String token = jwtProvider.createAccessToken(Long.valueOf("3287382897"));
        System.out.println(">>>>>>>>>>>>>> token = " + token);
    }

    @Test
    public void 토큰_검증하기() {
        String token = jwtProvider.createAccessToken(Long.valueOf("3287382897"));
        System.out.println(token);
        jwtProvider.isValidate(token);
    }
}
