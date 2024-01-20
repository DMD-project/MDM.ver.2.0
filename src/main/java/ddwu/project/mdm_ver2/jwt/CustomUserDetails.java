package ddwu.project.mdm_ver2.jwt;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/* Authentication을 담고 있는 UserDetails 인터페이스 상속
   SecurityContextHolder에 담을 Authentication 객체를 만들 때 사용 */

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {

    private Long userCode;
    private String kakaoEmail;
    private String userRole;

    /* 해당 user 권한 목록 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getUserRole();
            }
        });
        return grantedAuthority;
    }

    /* 비밀번호 */
    @Override
    public String getPassword() {
        return null;
    }

    /* pk 값 */
    @Override
    public String getUsername() {
        return String.valueOf(userCode);
    }

    public String getUserRole() {
        return userRole;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
