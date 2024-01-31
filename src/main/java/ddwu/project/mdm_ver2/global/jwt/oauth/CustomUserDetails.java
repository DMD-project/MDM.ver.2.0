package ddwu.project.mdm_ver2.global.jwt.oauth;

import ddwu.project.mdm_ver2.domain.user.entity.Role;
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

    private String email;
    private Role role;

    /* 해당 user 권한 목록 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getUserRole().getKey();
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
        return email;
    }

    public Role getUserRole() {
        return role;
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
