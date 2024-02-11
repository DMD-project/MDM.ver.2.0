package ddwu.project.mdm_ver2.domain.mypage.service;

import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public boolean checkNicknameDup(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
