package ddwu.project.mdm_ver2.domain.user.repository;

import ddwu.project.mdm_ver2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User saveAndFlush(User userInfo); //save(+update)

    boolean existsByKakaoEmail(String kakaoEmail);

    User findByKakaoEmail(String kakaoEmail);

    @Transactional
    void deleteByKakaoEmail(String kakaoEmail);

    Optional<User> findByUserCode(long userCode);

    boolean existsByUserNickname(String nickname);
}
