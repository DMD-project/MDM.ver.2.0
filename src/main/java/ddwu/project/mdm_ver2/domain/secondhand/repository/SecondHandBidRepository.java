package ddwu.project.mdm_ver2.domain.secondhand.repository;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecondHandBidRepository extends JpaRepository<SecondHandBid, Long> {

    Optional<SecondHandBid> findById(Long shBidId);
    SecondHandBid saveAndFlush(SecondHandBid secondHanBid);

    void deleteById(Long ShBidId);
}
