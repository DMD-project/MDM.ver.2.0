package ddwu.project.mdm_ver2.domain.grouppurchase.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.category.repository.CategoryRepository;
import ddwu.project.mdm_ver2.domain.grouppurchase.dto.GroupPurchaseRequest;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GPStatus;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.repository.GroupPurchaseRepository;
import ddwu.project.mdm_ver2.domain.order.entity.Order;
import ddwu.project.mdm_ver2.domain.order.repository.OrderRepository;
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

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GroupPurchaseService {

    private final GroupPurchaseRepository groupPurchaseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    // 공동구매상품 등록
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
    public CustomResponse<List<GroupPurchase>> findAllGroupPurchases() {
        try {
            List<GroupPurchase> groupPurchaseList = groupPurchaseRepository.findAll();

            return CustomResponse.onSuccess(groupPurchaseList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매 정렬
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


    // 특정 공동구매 상품 조회
    public CustomResponse<GroupPurchase> getGroupPurchase(Long gpId) {
        try {
            GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                    .orElseThrow(() -> new NotFoundException("공동구매 상품을 찾을 수 없습니다."));

            return CustomResponse.onSuccess(groupPurchase);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }


    // userEmail에 해당하는 주문 정보를 출력하는 메서드
    public CustomResponse<List<Order>> getGroupPurchasesByUser(Principal principal) {
        try {
            if (principal == null) {
                return CustomResponse.onFailure(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.");
            }

            String userEmail = principal.getName(); // Get the email of the currently authenticated user
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

            List<Order> orders = orderRepository.findByEmailAndGroupPurchaseIsNotNull(userEmail); // Find orders associated with the user's email where groupPurchase is not null
            return CustomResponse.onSuccess(orders);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 공동구매 전체 개수 조회
    public CustomResponse<Long> countAllGroupPurchases() {
        return CustomResponse.onSuccess(groupPurchaseRepository.count());
    }

    // 공동구매 참여하기 - 주문
    public CustomResponse<String> joinGroupPurchase(Principal principal, Long gpId, int purchasedQty) {
        try {
            if (principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "공동구매에 참여할 수 없습니다.");
            } else {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "email", principal.getName()));

                GroupPurchase groupPurchase = groupPurchaseRepository.findById(gpId)
                        .orElseThrow(() -> new ResourceNotFoundException("GroupPurchase", "gpId", gpId));

                boolean alreadyJoined = orderRepository.existsByEmailAndGroupPurchase(user.getEmail(), groupPurchase);
                if (alreadyJoined) {
                    return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "이미 해당 공동구매에 참여하였습니다.");
                }

                if (groupPurchase.getState() == GPStatus.ONGOING || groupPurchase.getState() == GPStatus.URGENT) {
                    if (purchasedQty > groupPurchase.getMaxQty()) {
                        return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "주문 수량이 최대 구매 가능 수량을 초과했습니다.");
                    }

                    Order order = new Order();
                    order.setEmail(user.getEmail());
                    order.setGroupPurchase(groupPurchase);
                    order.setPrice(groupPurchase.getPrice() * purchasedQty);
                    order.setQty(purchasedQty);

                    orderRepository.save(order);

                    int nowQty = groupPurchase.getNowQty() + purchasedQty;
                    groupPurchase.setNowQty(nowQty);
                    groupPurchaseRepository.save(groupPurchase);

                    return CustomResponse.onSuccess("공동구매 참여가 완료되었습니다.");

                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "공동구매에 참여 가능한 기간이 아닙니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //  공동구매 검색
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

    public void updateGroupPurchaseStatus() {
        List<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll();
        for (GroupPurchase groupPurchase : groupPurchases) {
            LocalDate currentDate = LocalDate.now();
            LocalDate endDate = groupPurchase.getEnd();

            if (groupPurchase.getNowQty() >= groupPurchase.getGoalQty() && currentDate.isAfter(endDate)) {
                groupPurchase.setState(GPStatus.ACHIEVED);
            } else if (currentDate.isAfter(endDate.minusDays(3))) {
                groupPurchase.setState(GPStatus.URGENT);
            } else {
                groupPurchase.setState(GPStatus.ONGOING);
            }
        }
    }

}
