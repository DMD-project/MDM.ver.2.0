package ddwu.project.mdm_ver2.domain.grouppurchase.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.category.repository.CategoryRepository;
import ddwu.project.mdm_ver2.domain.grouppurchase.dto.GroupPurchaseRequest;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GPStatus;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchaseParticipant;
import ddwu.project.mdm_ver2.domain.grouppurchase.repository.GroupPurchaseParticipantRepository;
import ddwu.project.mdm_ver2.domain.grouppurchase.repository.GroupPurchaseRepository;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.exception.ResourceNotFoundException;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupPurchaseService {

    private final GroupPurchaseRepository groupPurchaseRepository;
    private final GroupPurchaseParticipantRepository participantRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // 공동구매상품 등록
    @Transactional
    public CustomResponse<GroupPurchase> addGroupPurchase(GroupPurchaseRequest request) {
        try {
            GroupPurchase groupPurchase = new GroupPurchase();
            groupPurchase.setName(request.getName());
            groupPurchase.setPrice(request.getPrice());
            groupPurchase.setContent(request.getContent());
            groupPurchase.setImgUrl(request.getImgUrl());
            groupPurchase.setState(GPStatus.ONGOING);
            groupPurchase.setNowQty(0);
            groupPurchase.setMaxQty(request.getMaxQty());
            groupPurchase.setGoalQty(request.getGoalQty());
            groupPurchase.setStart(request.getStart());
            groupPurchase.setEnd(request.getEnd());

            Category category = categoryRepository.findByCateCode(request.getCateCode());
            groupPurchase.setCategory(category);

            GroupPurchase savedGroupPurchase = groupPurchaseRepository.save(groupPurchase);
            return CustomResponse.onSuccess(savedGroupPurchase);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매상품 수정
    @Transactional
    public CustomResponse<GroupPurchase> updateGroupPurchase(Long gpId,
                                                             GroupPurchaseRequest request) {
        try {
            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                    .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));

            groupPurchase.setName(request.getName());
            groupPurchase.setPrice(request.getPrice());
            groupPurchase.setContent(request.getContent());
            groupPurchase.setImgUrl(request.getImgUrl());
            groupPurchase.setMaxQty(request.getMaxQty());
            groupPurchase.setGoalQty(request.getGoalQty());
            groupPurchase.setStart(request.getStart());
            groupPurchase.setEnd(request.getEnd());

            Category category = categoryRepository.findByCateCode(request.getCateCode());
            groupPurchase.setCategory(category);

            GroupPurchase updatedGroupPurchase = groupPurchaseRepository.save(groupPurchase);
            return CustomResponse.onSuccess(updatedGroupPurchase);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매상품 삭제
    @Transactional
    public CustomResponse<String> deleteGroupPurchase(Long gpId) {
        try {
            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                    .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));

            groupPurchaseRepository.delete(groupPurchase);

            return CustomResponse.onSuccess("공동구매상품이 삭제되었습니다.");
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매 목록 조회
    @Transactional
    public CustomResponse<List<GroupPurchase>> findAllGroupPurchases() {
        try {
            List<GroupPurchase> groupPurchaseList = groupPurchaseRepository.findAll();

            return CustomResponse.onSuccess(groupPurchaseList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매 정렬
    @Transactional
    public CustomResponse<List<GroupPurchase>> sortGroupPurchase(String sort, String cateCode) {
        try {
            List<GroupPurchase> sortList;
            if (cateCode != null && !cateCode.isEmpty()) {
                sortList = sortGroupPurchasesByCategory(sort, cateCode);
            } else {
                switch (sort) {
                    case "lowprice":
                        sortList = groupPurchaseRepository.findAllByOrderByPriceAsc();
                        break;
                    case "highprice":
                        sortList = groupPurchaseRepository.findAllByOrderByPriceDesc();
                        break;
                    case "newest":
                        sortList = groupPurchaseRepository.findAllByOrderByIdDesc();
                        break;
                    default:
                        sortList = groupPurchaseRepository.findAll();
                        break;
                }
            }
            return CustomResponse.onSuccess(sortList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 카테고리 분류 후 공동구매 정렬
    @Transactional
    public List<GroupPurchase> sortGroupPurchasesByCategory(String sort, String cateCode) {
        List<GroupPurchase> productList;

        switch (sort) {
            case "lowprice":
                productList = groupPurchaseRepository.findAllByCategoryCateCodeOrderByPriceAsc(cateCode);
                break;
            case "highprice":
                productList = groupPurchaseRepository.findAllByCategoryCateCodeOrderByPriceDesc(cateCode);
                break;
            case "newest":
                productList = groupPurchaseRepository.findAllByCategoryCateCodeOrderByIdDesc(cateCode);
                break;
            default:
                productList = groupPurchaseRepository.findAllByCategoryCateCode(cateCode);
                break;
        }

        return productList;
    }


    // 특정 공동구매 총 참여자 조회
    @Transactional
    public CustomResponse<List<GroupPurchaseParticipant>> getGroupPurchase(Long gpId) {
        try {
            List<GroupPurchaseParticipant> participants = participantRepository.findByGroupPurchaseId(gpId);
            return CustomResponse.onSuccess(participants);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 특정 사용자가 참여한 모든 공동구매 조회
    @Transactional
    public CustomResponse<List<GroupPurchase>> getGroupPurchasesByUser(String userEmail) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

            List<GroupPurchaseParticipant> userParticipants = participantRepository.findByUser(user);
            List<GroupPurchase> groupPurchases = new ArrayList<>();

            for (GroupPurchaseParticipant participant : userParticipants) {
                groupPurchases.add(participant.getGroupPurchase());
            }

            return CustomResponse.onSuccess(groupPurchases);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매 전체 개수 조회
    @Transactional
    public CustomResponse<Long> countAllGroupPurchases() {
        return CustomResponse.onSuccess(groupPurchaseRepository.count());
    }

    //공동구매 참여하기
    @Transactional
    public CustomResponse<String> joinGroupPurchase(Long gpId, String userEmail, int purchasedQty) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

            if (isUserAlreadyJoined(gpId, user)) {
                return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "이미 공동구매에 참여한 사용자입니다.");
            }
            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                    .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));

            if (groupPurchase.getState() == GPStatus.ONGOING || groupPurchase.getState() == GPStatus.URGENT) {
                if(purchasedQty > groupPurchase.getMaxQty()) {
                    return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "주문 수량이 최대 구매 가능 수량을 초과했습니다.");
                }

                int now = groupPurchase.getNowQty();
                groupPurchase.setNowQty(now + purchasedQty);

                GroupPurchaseParticipant participant = new GroupPurchaseParticipant();
                participant.setGroupPurchase(groupPurchase);
                participant.setUser(user);
                participant.setPurchasedQty(purchasedQty);

                GroupPurchaseParticipant savedParticipant = participantRepository.save(participant);
                groupPurchase.getParticipants().add(savedParticipant);
                groupPurchaseRepository.save(groupPurchase);

                return CustomResponse.onSuccess("공동구매 참여가 완료되었습니다.");

            } else {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "공동구매에 참여 가능한 기간이 아닙니다.");
            }
        } catch (
                Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    private boolean isUserAlreadyJoined(Long gpId, User user) {
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));

        for (GroupPurchaseParticipant participant : groupPurchase.getParticipants()) {
            if (participant.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    // 공동구매 참여 취소
    @Transactional
    public CustomResponse<String> cancelGroupPurchase(Long gpId, String userEmail) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                    .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));

            // 참여자 목록에서 해당 사용자 찾기
            GroupPurchaseParticipant participantToRemove = null;
            for (GroupPurchaseParticipant participant : groupPurchase.getParticipants()) {
                if (participant.getUser().equals(user)) {
                    participantToRemove = participant;
                    break;
                }
            }

            if (participantToRemove != null) {
                groupPurchase.setNowQty(groupPurchase.getNowQty() - participantToRemove.getPurchasedQty());
                participantRepository.delete(participantToRemove);
                groupPurchase.getParticipants().remove(participantToRemove);

                groupPurchaseRepository.save(groupPurchase);

                return CustomResponse.onSuccess("공동구매 참여가 취소되었습니다.");
            } else {
                return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "사용자가 공동구매에 참여하지 않았거나 취소 권한이 없습니다.");
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }


    //  공동구매 검색
    @Transactional
    public CustomResponse<List<GroupPurchase>> searchGroupPurchase(String keyword) {
        try {
            if (StringUtils.isBlank(keyword)) {
                return CustomResponse.onSuccess(Collections.emptyList());
            }

            List<GroupPurchase> searchResults = groupPurchaseRepository.findByNameContainingIgnoreCase(keyword);
            return CustomResponse.onSuccess(searchResults);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @Transactional
    public void updateGroupPurchaseStatus() {
        List<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll();
        for (GroupPurchase groupPurchase : groupPurchases) {
            Date currentDate = new Date();
            if (groupPurchase.getNowQty() >= groupPurchase.getGoalQty() && currentDate.after(groupPurchase.getEnd())) {
                groupPurchase.setState(GPStatus.ACHIEVED);
            } else if (currentDate.after(new Date(groupPurchase.getEnd().getTime() - 3 * 24 * 60 * 60 * 1000))) {
                groupPurchase.setState(GPStatus.URGENT);
            } else {
                groupPurchase.setState(GPStatus.ONGOING);
            }
        }
    }

    // 공동구매 결제
//    @Transactional
//    public CustomResponse<Void> payGroupPurchase(Long gpId, String userEmail) {
//        try {
//            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
//                    .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));
//
//            // 결제 로직 추가
//
//            return CustomResponse.onSuccess(null);
//        } catch (Exception e) {
//            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//        }
//    }

    public void clearUserDate(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<GroupPurchaseParticipant> participantList = participantRepository.findByUser(user);
        for(GroupPurchaseParticipant participant : participantList) {
            GroupPurchase groupPurchase = groupPurchaseRepository.findById(participant.getGroupPurchase().getId())
                    .orElseThrow(() -> new NotFoundException("공동구매 상품을 찾을 수 없습니다."));

            int updateQty = groupPurchase.getNowQty() - participant.getPurchasedQty();
            participantRepository.deleteById(participant.getId());
            groupPurchase.setNowQty(updateQty);
        }
    }

}
