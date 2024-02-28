package ddwu.project.mdm_ver2.domain.secondhand.service;

import ddwu.project.mdm_ver2.domain.secondhand.dto.shBid.SecondHandBidRequest;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import ddwu.project.mdm_ver2.domain.secondhand.dto.shBid.SecondHandBidResponse;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandRepository;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandBidRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SecondHandBidService {

    private final SecondHandBidRepository shBidRepository;
    private final SecondHandRepository secondHandRepository;
    private final UserRepository userRepository;

    /* 전체 요청 정렬 */
    public CustomResponse<List<SecondHandBidResponse>> sortShBid(Principal principal, Long shId, String sort) {
        try {
            List<SecondHandBidResponse> sortedBidList = new ArrayList<SecondHandBidResponse>();
            switch (sort) {
                case "lowprice":
                    sortedBidList =
                            getSecondHandBidList(principal, shBidRepository.findAllBySecondHandIdOrderByPriceAsc(shId));
                    break;
                case "highprice":
                    sortedBidList =
                            getSecondHandBidList(principal, shBidRepository.findAllBySecondHandIdOrderByPriceDesc(shId));
                    break;
                case "newest":
                    sortedBidList =
                            getSecondHandBidList(principal, shBidRepository.findAllBySecondHandIdOrderByIdDesc(shId));
                    break;
            }
            return CustomResponse.onSuccess(sortedBidList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    /* 가격 제안 등록 (댓글 등록) */
    public CustomResponse<SecondHandBid> addShBid(Principal principal, Long shId, SecondHandBidRequest request) {
        try {
            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 요청을 할 수 없습니다.");
            } else {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                SecondHand secondHand = secondHandRepository.findById(shId)
                        .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

                SecondHandBid secondHandBid = SecondHandBid.builder()
                        .secondHand(secondHand)
                        .bidUserId(user.getId())
                        .price(request.getPrice())
                        .build();
                shBidRepository.saveAndFlush(secondHandBid);

                secondHand.setBidCnt(shBidRepository.countBySecondHandId(shId));
                secondHandRepository.saveAndFlush(secondHand);

                return CustomResponse.onSuccess(secondHandBid);
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 제안 수정 */
    public CustomResponse<SecondHandBid> updateShBid(Principal principal, Long shBidId, SecondHandBidRequest request) {
        try {
            SecondHandBid secondHandBid = shBidRepository.findById(shBidId)
                    .orElseThrow(() -> new NotFoundException("상품 요청을 찾을 수 없습니다."));

            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 요청을 수정할 수 없습니다.");
            } else {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                if((user.getId()) == (secondHandBid.getBidUserId())) {
                    if(secondHandBid != null) {
                        secondHandBid.setPrice(request.getPrice());
                        SecondHandBid updateSecondHandBid = shBidRepository.saveAndFlush(secondHandBid);

                        return CustomResponse.onSuccess(updateSecondHandBid);
                    } else {
                        return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), "상품 요청을 찾을 수 없습니다.");
                    }
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "요청을 수정할 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 제안 삭제 */
    @Transactional
    public CustomResponse<Void> deleteSecondHandBid(Principal principal, Long shId, Long shBidId) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            SecondHandBid secondHandBid = shBidRepository.findById(shBidId)
                    .orElseThrow(() -> new NotFoundException("상품 요청을 찾을 수 없습니다."));

            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 상품을 수정할 수 없습니다.");
            } else {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                if((user.getId()) == (secondHandBid.getBidUserId())) {
                    shBidRepository.deleteById(shBidId);

                    secondHand.setBidCnt(shBidRepository.countBySecondHandId(shId));
                    secondHandRepository.saveAndFlush(secondHand);

                    return CustomResponse.onSuccess(null);
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "요청을 삭제할 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 특정 사용자 중고거래 요청 가져오기 */
    public CustomResponse<List<SecondHandBid>> getSecondHandBidListByUser (String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        try {
            List<SecondHandBid> secondHandBidList = shBidRepository.findAllByBidUserId(user.getId());
            return CustomResponse.onSuccess(secondHandBidList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    public List<SecondHandBidResponse> getSecondHandBidList(Principal principal, List<SecondHandBid> tmpList) {
        List<SecondHandBidResponse> shBidList = new ArrayList<>();

        if(principal == null) {
            for(SecondHandBid bid : tmpList) {
                SecondHandBidResponse shBidResponse =
                        new SecondHandBidResponse(bid.getId(),
                                bid.getSecondHand().getId(),
                                bid.getBidUserId(),
                                bid.getPrice(),
                                'n');
                shBidList.add(shBidResponse);
            }
        } else {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            for(SecondHandBid bid : tmpList) {
                Character bidUserState;
                if(user.getId() == bid.getBidUserId()) {
                    bidUserState = 'y';
                } else {
                    bidUserState = 'n';
                }
                SecondHandBidResponse shBidResponse =
                        new SecondHandBidResponse(bid.getId(),
                                bid.getSecondHand().getId(),
                                bid.getBidUserId(),
                                bid.getPrice(),
                                bidUserState);
                shBidList.add(shBidResponse);
            }
        }
        return shBidList;
    }

    public void clearUserData(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<SecondHandBid> shBidList = shBidRepository.findAllByBidUserId(userId);
        for(SecondHandBid bid: shBidList) {
            SecondHand secondHand = secondHandRepository.findById(bid.getSecondHand().getId())
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            shBidRepository.deleteById(bid.getId());
            secondHand.setBidCnt(shBidRepository.countBySecondHandId(secondHand.getId()));
        }
    }
}
