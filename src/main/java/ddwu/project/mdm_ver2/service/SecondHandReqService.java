package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.SecondHand;
import ddwu.project.mdm_ver2.domain.SecondHandReq;
import ddwu.project.mdm_ver2.dto.SecondHandReqRequest;
import ddwu.project.mdm_ver2.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.repository.SecondHandRepository;
import ddwu.project.mdm_ver2.repository.SecondHandReqRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecondHandReqService {

    private final SecondHandReqRepository shReqRepository;
    private final SecondHandRepository secondHandRepository;

    /* 가격 제안 등록 (댓글 등록) */
    public SecondHandReq addShReq(Long shID, SecondHandReqRequest request) {
        SecondHand secondHand = secondHandRepository.findByShID(shID);

        SecondHandReq secondHandReq = SecondHandReq.builder()
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
        SecondHandReq secondHandReq = shReqRepository.findByShReqID(shReqID);

        if(secondHandReq == null) {
            throw new ResourceNotFoundException("secondHandReq", "shReqID", shReqID);
        }
        shReqRepository.deleteByShReqID(shReqID);
    }
}
