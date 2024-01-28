package ddwu.project.mdm_ver2.jwt;

import ddwu.project.mdm_ver2.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


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
//                .apply(new MyCustomDsl());
        httpSecurity
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().permitAll());
//        httpSecurity
//                .authorizeRequests()
////                .requestMatchers(HttpMethod.GET, "/").authenticated();
////                .requestMatchers("/login").permitAll()
////                .requestMatchers("/kakao").permitAll()
////                .requestMatchers("/kakaoJoin").permitAll()
//                .anyRequest().permitAll();
//                .requestMatchers(HttpMethod.GET, "/*").authenticated();
        httpSecurity
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// jwt 사용하는 경우 사용

        httpSecurity
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
/*
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http
//            cors 오류를 해결하기 위해 Controller 에 @CrossOrigin 을 붙여주는 방법도 있지만
//				이 방식은 필터 추가와 다르게 인증이 필요 없는 url 만 처리해줌
                    .addFilter(corsFilter()) // cors 에 대해 허락하는 필터
                    .addFilter(new JwtFilter(jwtProvider));
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 허용할 URL
        config.addAllowedHeader("*"); // 허용할 Header
        config.addAllowedMethod("*"); // 허용할 Http Method
        config.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));
        source.registerCorsConfiguration("/**", config); // 모든 Url에 대해 설정한 CorsConfiguration 등록
        return new CorsFilter(source);
    }

 */
}