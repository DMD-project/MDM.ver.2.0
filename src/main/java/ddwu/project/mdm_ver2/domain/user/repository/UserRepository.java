package ddwu.project.mdm_ver2.domain.user.repository;

import ddwu.project.mdm_ver2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User saveAndFlush(User userInfo); //save(+update)

    boolean existsByUserEmail(String userEmail);

    User findByUserEmail(String userEmail);

    @Transactional
    void deleteByUserEmail(String userEmail);

    Optional<User> findByUserCode(long userID);

    boolean existsByUserNickname(String nickname);
}
