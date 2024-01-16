package ddwu.project.mdm_ver2.validator;

import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.repository.UserRepository;
import org.springframework.validation.Errors;

public class CheckNicknameValidator extends AbstractValidator<String> {

    private UserRepository userRepository;

    @Override
    protected void doValidate(String nickname, Errors errors) {
        if(userRepository.existsByUserNickname(nickname)) {
            errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용 중인 닉네임 입니다.");
        }
    }
}
