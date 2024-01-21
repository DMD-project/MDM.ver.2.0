package ddwu.project.mdm_ver2.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/* API 요청 들어왔을 때 컨트롤러로 넘어가기 전 토큰 확인 필터 함수 */
@Slf4j
@Builder
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        log.info("path: {}", path);
        log.info("request: {}", request.getHeaderNames());

        // login ->  건너뜀
        if(path.startsWith("/kakao")) {  //"로그인 요청 API"
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        log.info("authorization header: {}", header);
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1];

//        Long userCode = jwtProvider.getKakaoUserCode(token);
//        log.info("userCode: {}", userCode);

        // refresh token 유효한지 확인, 컨트롤러에서 토큰 재발행
//        if(!(path.startsWith("/* token 재발행 API */") && jwtProvider.isRefreshToken(token)) || jwtProvider.isAccessToken(token)) {
//            throw new JwtException("");
//        }

        if(token != null && jwtProvider.isValidate(token)) {
            Authentication authenticationToken = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

//        authenticationToken.getDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        /* SecurityContextHolder에 담기지 않고 chain.doFilter를 통해서
        다음 필터로 넘어간다면 CustomEntryPoint로 넘어감*/
        filterChain.doFilter(request, response);
    }
}
