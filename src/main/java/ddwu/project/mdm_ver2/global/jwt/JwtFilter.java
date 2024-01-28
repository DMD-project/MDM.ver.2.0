package ddwu.project.mdm_ver2.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
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

        String header = request.getHeader("Authorization");
        log.info("Authorization header: {}", header);

//        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        String token = null;
        if(request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer ")){
            token = request.getHeader("Authorization").split(" ")[1];
        }

        if(token != null && jwtProvider.isValidate(token)) {
            Authentication authenticationToken = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("Security Context에 '{}' 인증 정보 저장했습니다", authenticationToken.getName());
        } else {
            log.info("유효한 JWT 토큰이 없습니다");
        }

//        authenticationToken.getDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        /* SecurityContextHolder에 담기지 않고 chain.doFilter를 통해서
        다음 필터로 넘어간다면 CustomEntryPoint로 넘어감*/
        filterChain.doFilter(request, response);
    }
}
