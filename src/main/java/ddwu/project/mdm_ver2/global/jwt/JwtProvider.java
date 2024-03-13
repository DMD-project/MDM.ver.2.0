package ddwu.project.mdm_ver2.global.jwt;

import ddwu.project.mdm_ver2.global.jwt.oauth.CustomUserDetails;
import ddwu.project.mdm_ver2.global.jwt.oauth.CustomUserDetailsService;
import ddwu.project.mdm_ver2.global.redis.RedisUtil;
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
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    private final CustomUserDetailsService userDetailsService;
    private final RedisUtil redisUtil;

    private static final Long accessTokenValidTime = Duration.ofMinutes(30).toMillis();
    private static final Long refreshTokenValidTime = Duration.ofDays(14).toMillis();

    public String createAccessToken(Long userId, String kakaoAccessToken) {
        return createToken(userId, kakaoAccessToken, "access", accessTokenValidTime);
    }

    public String createRefreshToken(Long userId, String kakaoAccessToken) {
        String refresh_token = createToken(userId, kakaoAccessToken, "refresh", refreshTokenValidTime);
        redisUtil.setDataExpire(String.valueOf(userId), refresh_token, refreshTokenValidTime);

        return refresh_token;
    }

    public String createToken(Long userId, String kakaoAccessToken, String type, Long tokenValidTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("kakaoAccessToken", kakaoAccessToken);

        log.info("create token: {}", type);
        return Jwts.builder()
                .setHeaderParam("type", type)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean isValidate(String token) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (JwtException e) {
            log.error("JwtException");
        } catch (IllegalArgumentException e) {
            log.error("Jwt claims string is empty");
        } catch (NullPointerException e) {
            log.error("Jwt Token is empty");
        }

        return false;
    }

    public Authentication getAuthentication(String token){
        log.info("getUserId: {}", getUserId(token));
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(this.getUserId(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public String getKakaoAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("kakaoAccessToken", String.class);
    }

    public Long getAccessTokenValidTime(String accessToken) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}