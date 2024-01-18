package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User saveAndFlush(User userInfo); //save(+update)

    boolean existsByUserCode(long userCode);

    User findByKakaoEmail(String kakaoEmail);

    @Transactional
    void deleteByUserCode(long userCode);

    Optional<User> findByUserCode(long userCode);

    boolean existsByUserNickname(String nickname);
}
