package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.SecondHand;
import ddwu.project.mdm_ver2.domain.SecondHandReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondHandReqRepository extends JpaRepository<SecondHand, Long> {

    SecondHandReq findByShReqID(Long shReqID);
    SecondHandReq saveAndFlush(SecondHandReq secondHandReq);

    void deleteByShReqID(Long ShReqID);
}
