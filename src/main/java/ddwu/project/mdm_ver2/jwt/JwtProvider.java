package ddwu.project.mdm_ver2.jwt;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;


/* jwt 생성 및 유효성 검증 컴포넌트 */
@Slf4j
@Component
@RequiredArgsConstructor
//@Service
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    private CustomUserDetailsService userDetailsService;

    private static final Long accessTokenValidTime = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
    private static final Long refreshTokenValidTime = Duration.ofDays(14).toMillis(); // 만료시간 2주

    /* access, refresh token 생성 */
    public String createAccessToken(Long userCode) {
        return createToken(userCode, "access", accessTokenValidTime);
    }

    public String createRefreshToken(Long userCode) {
        return createToken(userCode, "refresh", refreshTokenValidTime);
    }

    public String createToken(Long userCode, String type, Long tokenValidTime) {
        Claims claims = Jwts.claims();
        claims.put("userCode", userCode);

        return Jwts.builder()
                .setHeaderParam("type", type)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /* token 유효, 만료 확인 */
    public boolean isValidate(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (JwtException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRefreshToken(String token) {
        Header header = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getHeader();

        if(header.get("type").toString().equals("refresh")) {
            return true;
        }
        return false;
    }

    public boolean isAccessToken(String token) {
        Header header = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getHeader();

        if(header.get("type").toString().equals("access")) {
            return true;
        }
        return false;
    }

//    public UsernamePasswordAuthenticationToken getAuthentication(String accessToken) {
//        return new UsernamePasswordAuthenticationToken(getKakaoUserCode(accessToken), "", createAuthorityList(getRole(accessToken)));
//    }

    /* Jwt Token에 담긴 유저 정보 DB에 검색,
    해당 유저의 권한 처리를 위해 Context에 담는 Authentication 객체를 반환 */
    public Authentication getAuthentication(String token){
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(this.getKakaoUserCode(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /* 회원 정보 조회 */
    public Long getKakaoUserCode(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userCode", Long.class);
    }

    private String getRole(String accessToken) {
        return (String) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody()
                .get("role", String.class);

    }
}
