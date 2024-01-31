package ddwu.project.mdm_ver2.global.jwt;

import ddwu.project.mdm_ver2.domain.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@Configuration
public class AuthenticationConfig {

    private UserService kakaoService;

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
                                .requestMatchers(new AntPathRequestMatcher("/cart"),
                                        new AntPathRequestMatcher("/cartItem"),
                                        new AntPathRequestMatcher("/secondHand/add"),
                                        new AntPathRequestMatcher("/favorite/{id}", "/favorite/{id}/**")).hasRole("USER")
                                .requestMatchers(new AntPathRequestMatcher("/**"),
                                        new AntPathRequestMatcher("/login")).permitAll());
//        httpSecurity
//                .authorizeRequests()
//                .requestMatchers(HttpMethod.GET, "/").authenticated();
//                .requestMatchers("/login").permitAll()
//                .requestMatchers("/kakao").permitAll()
//                .requestMatchers("/").permitAll()
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
