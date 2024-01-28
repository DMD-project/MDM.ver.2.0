package ddwu.project.mdm_ver2.global.jwt.oauth;

import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        User user = userRepository.findByUserCode(Long.parseLong(userCode))
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if(user != null) {
            return CustomUserDetails.builder()
                    .kakaoEmail(user.getKakaoEmail())
                    .userRole(user.getUserRole())
                    .build();

        }

        return null;
    }
}
