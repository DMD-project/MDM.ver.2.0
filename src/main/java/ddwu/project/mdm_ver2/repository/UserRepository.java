package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User saveAndFlush(User userInfo);

    boolean existsByUserCode(long userCode);



    void deleteByKakaoEmail(String kakaoEmail);

}
