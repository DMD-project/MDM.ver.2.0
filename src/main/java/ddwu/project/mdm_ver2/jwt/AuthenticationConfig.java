package ddwu.project.mdm_ver2.jwt;

import ddwu.project.mdm_ver2.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class AuthenticationConfig {

    private KakaoService kakaoService;

    private JwtProvider jwtProvider;

    public AuthenticationConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());
        httpSecurity
                .authorizeRequests()
//                .requestMatchers(HttpMethod.GET, "/").authenticated();
//                .requestMatchers("/login").permitAll()
//                .requestMatchers("/kakao").permitAll()
//                .requestMatchers("/kakaoJoin").permitAll()
                .anyRequest().permitAll();
//                .requestMatchers(HttpMethod.GET, "/*").authenticated();
        httpSecurity
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// jwt 사용하는 경우 사용

        httpSecurity
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
}

