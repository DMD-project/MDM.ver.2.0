package ddwu.project.mdm_ver2.global.jwt;

import ddwu.project.mdm_ver2.global.redis.RedisUtil;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AuthenticationConfig {

    private JwtProvider jwtProvider;
    private RedisUtil redisUtil;

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());
//                .apply(new MyCustomDsl());
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
//                                new AntPathRequestMatcher("/cart/**"),
//                                new AntPathRequestMatcher("/cartItem/**"),
//                                new AntPathRequestMatcher("/favType/**"),
//                                new AntPathRequestMatcher("/gp/participants"),
//                                new AntPathRequestMatcher("/gp/order/{gpId}/{purchasedQty}"),
//                                new AntPathRequestMatcher("/gp/cancel/{gpId}"),
//                                new AntPathRequestMatcher("/gp/pay/{gpId}"),
//                                new AntPathRequestMatcher("/mypage/**"),
//                                new AntPathRequestMatcher("/review/{prodId}/add"),
//                                new AntPathRequestMatcher("/review/{prodId}/update/{reviewId}"),
//                                new AntPathRequestMatcher("/review/{prodId}/delete/{reviewId}"),
//                                new AntPathRequestMatcher("/secondhand/add"),
//                                new AntPathRequestMatcher("/secondhand/**/{shId}"),
//                                new AntPathRequestMatcher("/shBid/{shId}/add"),
//                                new AntPathRequestMatcher("/shBid/{shId}/update/{shBidId}"),
//                                new AntPathRequestMatcher("/shBid/{shId}/delete/{shBidId}"),
                                new AntPathRequestMatcher("/reissue"),
                                new AntPathRequestMatcher("/kakao/logout"),
                                new AntPathRequestMatcher("/kakao/withdrawal"))
                        .hasRole("USER")

//                        .requestMatchers(new AntPathRequestMatcher("/admin/gp/**"),
//                                new AntPathRequestMatcher("/admin/product/**"),
//                                new AntPathRequestMatcher("/secondhand/update/{shId}/state/{state}"))
//                        .hasRole("ADMIN")

                        .requestMatchers(new AntPathRequestMatcher("/**"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/kakao/"),
                                new AntPathRequestMatcher("/kakao/ios"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/gp/list"),
                                new AntPathRequestMatcher("/gp/sort/category"),
                                new AntPathRequestMatcher("/gp/{id}"),
                                new AntPathRequestMatcher("/gp/count"),
                                new AntPathRequestMatcher("/gp/search/{keyword}"),
                                new AntPathRequestMatcher("/product/**"),
                                new AntPathRequestMatcher("/review/{prodId}/sort"),
                                new AntPathRequestMatcher("/secondhand"),
                                new AntPathRequestMatcher("/secondhand/sort/category"),
                                new AntPathRequestMatcher("/secondhand/{shId}"),
                                new AntPathRequestMatcher("/secondhand/search/{keyword}"),
                                new AntPathRequestMatcher("/shBid/{shId}/sort"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/**/admin/**"))
                        .permitAll());
        httpSecurity
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// jwt 사용하는 경우 사용
        httpSecurity
                .addFilterBefore(new JwtFilter(jwtProvider, redisUtil), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
/*
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
