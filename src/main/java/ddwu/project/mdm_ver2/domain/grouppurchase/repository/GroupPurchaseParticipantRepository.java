package ddwu.project.mdm_ver2.domain.grouppurchase.repository;

import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchaseParticipant;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPurchaseParticipantRepository  extends JpaRepository<GroupPurchaseParticipant, Long> {
    List<GroupPurchaseParticipant> findByGroupPurchaseId(Long gpId);
    List<GroupPurchaseParticipant> findByUser(User user);
}
