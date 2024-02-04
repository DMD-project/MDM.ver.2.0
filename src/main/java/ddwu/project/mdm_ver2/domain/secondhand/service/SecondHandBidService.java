package ddwu.project.mdm_ver2.domain.secondhand.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandRequest;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandBidRequest;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandRepository;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandBidRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class SecondHandBidService {

    private final SecondHandBidRepository shBidRepository;
    private final SecondHandRepository secondHandRepository;

    /* 전체 요청 정렬 */
    public CustomResponse<List<SecondHandBid>> sortShBid(Long shId, String sort) {
        try {
            List<SecondHandBid> sortedBidList;

            switch (sort) {
                case "lowprice":
                    sortedBidList = shBidRepository.findAllBySecondHandIdOrderByPriceAsc(shId);
                    break;
                case "highprice":
                    sortedBidList = shBidRepository.findAllBySecondHandIdOrderByPriceDesc(shId);
                    break;
                case "newest":
                    sortedBidList = shBidRepository.findAllBySecondHandIdOrderByIdDesc(shId);
                    break;
                default:
                    sortedBidList = shBidRepository.findAllBySecondHandId(shId);
                    break;
            }

            return CustomResponse.onSuccess(sortedBidList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 가격 제안 등록 (댓글 등록) */
    public CustomResponse<SecondHandBid> addShBid(Long shId, SecondHandBidRequest request) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            setSecondHandBidCnt(secondHand, secondHand.getBidCnt(), "add");

            SecondHandBid secondHandBid = SecondHandBid.builder()
                    .id(request.getId())
                    .secondHand(secondHand)
                    .bidUserId(request.getBidUserId())
                    .price(request.getPrice())
                    .build();

            shBidRepository.saveAndFlush(secondHandBid);

            return CustomResponse.onSuccess(secondHandBid);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 제안 수정 */
    public CustomResponse<SecondHandBid> updateShBid(Long shBidId, SecondHandBidRequest request) {
        try {
            SecondHandBid secondHandBid = shBidRepository.findById(shBidId)
                    .orElseThrow(() -> new NotFoundException("상품 요청을 찾을 수 없습니다."));

            if(secondHandBid != null) {
                secondHandBid.setPrice(request.getPrice());
                SecondHandBid updateSecondHandBid = shBidRepository.saveAndFlush(secondHandBid);

                return CustomResponse.onSuccess(updateSecondHandBid);
            }
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), "상품 요청을 찾을 수 없습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 제안 삭제 */
    @Transactional
    public CustomResponse<Void> deleteSecondHandBid(Long shId, Long shBidId) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            SecondHandBid secondHandBid = shBidRepository.findById(shBidId)
                    .orElseThrow(() -> new NotFoundException("상품 요청을 찾을 수 없습니다."));

            if(secondHandBid == null) {
                throw new ResourceNotFoundException("secondHandBid", "shBidId", shBidId);
            }

            setSecondHandBidCnt(secondHand, secondHand.getBidCnt(), "delete");
            shBidRepository.deleteById(shBidId);

            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public void setSecondHandBidCnt(SecondHand secondHand, int cnt, String calc) {
        if(secondHand != null) {
            switch (calc) {
                case "add":
                    secondHand.setBidCnt(cnt + 1);
                    break;
                case "delete":
                    if(secondHand.getBidCnt() != 0) {
                        secondHand.setBidCnt(cnt - 1);
                    }
                    break;
            }
        }
    }
}
