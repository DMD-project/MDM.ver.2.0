package ddwu.project.mdm_ver2.jwt;

import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        User user = userRepository.findByUserCode(Long.parseLong(userCode))
                .orElseThrow(() -> new UsernameNotFoundException(""));

        if(user != null) {
            return CustomUserDetails.builder()
                    .userCode(user.getUserCode())
                    .kakaoEmail(user.getKakaoEmail())
                    .userRole(user.getUserRole())
                    .build();

        }

        return null;
    }
}
