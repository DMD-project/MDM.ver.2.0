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
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
public class SecondHandBidService {

    private final SecondHandBidRepository shBidRepository;
    private final SecondHandRepository secondHandRepository;

    /* 가격 제안 등록 (댓글 등록) */
    public SecondHandBid addShBid(Long shId, SecondHandBidRequest request) {
        SecondHand secondHand = secondHandRepository.findById(shId)
                .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

        SecondHandBid secondHandBid = SecondHandBid.builder()
                .id(request.getId())
                .secondHand(secondHand)
                .bidUserId(request.getBidUserId())
                .price(request.getPrice())
                .build();

        return shBidRepository.saveAndFlush(secondHandBid);
    }

    /* 제안 수정 */


    /* 제안 삭제 */
    @Transactional
    public void deleteSecondHandBid(Long shBidId) {
        SecondHandBid secondHandBid = shBidRepository.findById(shBidId)
                .orElseThrow(() -> new NotFoundException("요청을 찾을 수 없습니다."));

        if(secondHandBid == null) {
            throw new ResourceNotFoundException("secondHandBid", "shBidId", shBidId);
        }
        shBidRepository.deleteById(shBidId);
    }
}
