package ddwu.project.mdm_ver2.domain.secondhand.service;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandBidRequest;
import ddwu.project.mdm_ver2.global.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandRepository;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandBidRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecondHandBidService {

    private final SecondHandBidRepository shReqRepository;
    private final SecondHandRepository secondHandRepository;

    /* 가격 제안 등록 (댓글 등록) */
    public SecondHandBid addShReq(Long shID, SecondHandBidRequest request) {
        SecondHand secondHand = secondHandRepository.findByShID(shID);

        SecondHandBid secondHandReq = SecondHandBid.builder()
                .shReqID(request.getShReqID())
                .secondHand(secondHand)
                .shReqUserID(request.getShReqUserID())
                .shReqPrice(request.getShReqPrice())
                .build();

        return shReqRepository.saveAndFlush(secondHandReq);
    }

    /* 제안 수정 */


    /* 제안 삭제 */
    @Transactional
    public void deleteSecondHandReq(Long shReqID) {
        SecondHandBid secondHandReq = shReqRepository.findByShReqID(shReqID);

        if(secondHandReq == null) {
            throw new ResourceNotFoundException("secondHandReq", "shReqID", shReqID);
        }
        shReqRepository.deleteByShReqID(shReqID);
    }
}
