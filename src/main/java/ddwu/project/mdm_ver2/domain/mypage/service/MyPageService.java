package ddwu.project.mdm_ver2.domain.mypage.service;

import ddwu.project.mdm_ver2.domain.mypage.dto.AddressRequest;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public CustomResponse<User> getUser(String userEmail) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
            return CustomResponse.onSuccess(user);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public boolean checkNicknameDup(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public CustomResponse<Void> setUserNickname(String userEmail, String nickname) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        try {
            user.setNickname(nickname);
            userRepository.saveAndFlush(user);
            return CustomResponse.onSuccess("닉네임이 수정되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public CustomResponse<Void> setUserAddress(String userEmail, AddressRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        try {
            user.setStreetAddr(request.getStreetAddr());
            user.setDetailAddr(request.getDetailAddr());
            user.setZipcode(request.getZipcode());
            userRepository.saveAndFlush(user);

            return CustomResponse.onSuccess("주소가 수정되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}