package ddwu.project.mdm_ver2.domain.user.repository;

import ddwu.project.mdm_ver2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User saveAndFlush(User user); //save(+update)

    boolean existsByEmail(String userEmail);

    Optional <User> findByEmail(String userEmail);

    @Transactional
    void deleteByEmail(String userEmail);

    Optional<User> findById(long userId);

    boolean existsByNickname(String nickname);
}
