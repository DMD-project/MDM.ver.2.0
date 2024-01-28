package ddwu.project.mdm_ver2.domain.secondhand.repository;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondHandBidRepository extends JpaRepository<SecondHandBid, Long> {

    SecondHandBid findByShReqID(Long shReqID);
    SecondHandBid saveAndFlush(SecondHandBid secondHandReq);

    void deleteByShReqID(Long ShReqID);
}
