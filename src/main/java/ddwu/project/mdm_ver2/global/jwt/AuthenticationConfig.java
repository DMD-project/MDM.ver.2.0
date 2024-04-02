package ddwu.project.mdm_ver2.global.jwt;

import ddwu.project.mdm_ver2.global.redis.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class AuthenticationConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JwtProvider jwtProvider;
    private RedisUtil redisUtil;
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable());
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                new AntPathRequestMatcher("/cart"),
                                new AntPathRequestMatcher("/cart/delete"),
//                                new AntPathRequestMatcher("/cartItem/**"),
                                new AntPathRequestMatcher("/favType/**"),
                                new AntPathRequestMatcher("/gp/participants"),
                                new AntPathRequestMatcher("/gp/order/{gpId}/{purchasedQty}"),
                                new AntPathRequestMatcher("/gp/cancel/{gpId}"),
                                new AntPathRequestMatcher("/gp/pay/{gpId}"),
                                new AntPathRequestMatcher("/mypage"),
                                new AntPathRequestMatcher("/mypage/check/{userNickname}"),
                                new AntPathRequestMatcher("/mypage/nickname/{userNickname}"),
                                new AntPathRequestMatcher("/mypage/address"),
                                new AntPathRequestMatcher("/mypage/favorite"),
                                new AntPathRequestMatcher("/mypage/review"),
                                new AntPathRequestMatcher("/mypage/gp"),
                                new AntPathRequestMatcher("/mypage/sh"),
                                new AntPathRequestMatcher("/mypage/shBid"),
                                new AntPathRequestMatcher("/mypage/order"),
                                new AntPathRequestMatcher("/review/{prodId}/add"),
                                new AntPathRequestMatcher("/review/{prodId}/update/{reviewId}"),
                                new AntPathRequestMatcher("/review/{prodId}/delete/{reviewId}"),
                                new AntPathRequestMatcher("/secondhand/add"),
                                new AntPathRequestMatcher("/secondhand/update/{shId}"),
                                new AntPathRequestMatcher("/secondhand/update/{shId}/state/{state}"),
                                new AntPathRequestMatcher("/secondhand/delete/{shId}"),
                                new AntPathRequestMatcher("/shBid/{shId}/add"),
                                new AntPathRequestMatcher("/shBid/{shId}/update/{shBidId}"),
                                new AntPathRequestMatcher("/shBid/{shId}/delete/{shBidId}"),
                                new AntPathRequestMatcher("/reissue"),
                                new AntPathRequestMatcher("/kakao/logout"),
                                new AntPathRequestMatcher("/kakao/withdrawal"))
                        .hasRole("USER")

                        .requestMatchers(new AntPathRequestMatcher("/admin/gp/**"),
                                new AntPathRequestMatcher("/admin/product/**"))
                        .hasRole("ADMIN")

                        .requestMatchers(new AntPathRequestMatcher("/**"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/kakao/"),
                                new AntPathRequestMatcher("/kakao/ios"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/**/view"),
                                new AntPathRequestMatcher("/gp/list"),
                                new AntPathRequestMatcher("/gp/sort/category"),
                                new AntPathRequestMatcher("/gp/{id}"),
                                new AntPathRequestMatcher("/gp/count"),
                                new AntPathRequestMatcher("/gp/search/{keyword}"),
                                new AntPathRequestMatcher("/product/**"),
                                new AntPathRequestMatcher("/review/{prodId}/sort"),
                                new AntPathRequestMatcher("/secondhand/list"),
                                new AntPathRequestMatcher("/secondhand/sort/category"),
                                new AntPathRequestMatcher("/secondhand/{shId}"),
                                new AntPathRequestMatcher("/secondhand/search/{keyword}"),
                                new AntPathRequestMatcher("/shBid/{shId}/sort"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/**/admin/**"))
                        .permitAll());
        httpSecurity
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity
                .addFilterBefore(new JwtFilter(jwtProvider, redisUtil), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
