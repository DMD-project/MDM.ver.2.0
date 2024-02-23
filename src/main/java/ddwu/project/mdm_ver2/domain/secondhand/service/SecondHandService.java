package ddwu.project.mdm_ver2.domain.secondhand.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.favorite.repository.FavoriteRepository;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.secondhand.dto.sh.SecondHandResponse;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.dto.sh.SecondHandRequest;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandBidRepository;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.domain.category.repository.CategoryRepository;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SecondHandService {

    private final SecondHandRepository secondHandRepository;
    private final SecondHandBidRepository secondHandBidRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteRepository favoriteRepository;
    private final SecondHandBidService secondHandBidService;

    /* 전체 상품 */
    public CustomResponse<List<SecondHand>> findAllSecondHand() {
        try {
            List<SecondHand> secondHandList = secondHandRepository.findAll();
            return CustomResponse.onSuccess(secondHandList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    /* 상품 정렬 */
    public CustomResponse<List<SecondHand>> sortSecondHand(String sort, String cateCode) {
        try {
            List<SecondHand> sortedSecondHandList;
            if(cateCode != null && !cateCode.isEmpty()) {
                sortedSecondHandList = sortSecondHandsByCategory(sort, cateCode);
            } else {
                switch (sort) {
                    case "lowprice":
                        sortedSecondHandList = secondHandRepository.findAllByOrderByPriceAsc();
                        break;
                    case "highprice":
                        sortedSecondHandList = secondHandRepository.findAllByOrderByPriceDesc();
                        break;
                    case "newest":
                        sortedSecondHandList = secondHandRepository.findAllByOrderByIdDesc();
                        break;
                    default:
                        sortedSecondHandList = secondHandRepository.findAll();
                        break;
                }
            }
            return CustomResponse.onSuccess(sortedSecondHandList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    /* 카테고리 내 상품 정렬 */
    public List<SecondHand> sortSecondHandsByCategory(String sort, String cateCode) {
        List<SecondHand> sortedSecondHandList;

        switch (sort) {
            case "lowprice":
                return secondHandRepository.findAllByCategoryCateCodeOrderByPriceAsc(cateCode);
            case "highprice":
                return secondHandRepository.findAllByCategoryCateCodeOrderByPriceDesc(cateCode);
            case "newest":
                return secondHandRepository.findAllByCategoryCateCodeOrderByIdDesc(cateCode);
            default:
                return secondHandRepository.findAllByCategoryCateCode(cateCode);
        }
//        return sortedSecondHandList;
    }

    /* 상품 상세 정보 */
    public CustomResponse<SecondHandResponse> getSecondHand(Principal principal, Long shId) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            SecondHandResponse response = setResponse(principal, shId);

            return CustomResponse.onSuccess(response);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    /* 상품 등록 */
    public CustomResponse<SecondHand> addSecondHand(String userEmail, SecondHandRequest request) {
        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            Category category = categoryRepository.findByCateCode(request.getCateCode());

            if (category == null) {
                throw new NotFoundException("카테고리를 찾을 수 없습니다: " + request.getCateCode());
            }

            SecondHand secondHand = SecondHand.builder()
                    .userId(user.getId())
                    .name(request.getName())
                    .category(category)
                    .price(request.getPrice())
                    .imgUrl(request.getImgUrl())
                    .content(request.getContent())
                    .state('n')
                    .build();

            SecondHand newSecondHand = secondHandRepository.saveAndFlush(secondHand);
            return CustomResponse.onSuccess(newSecondHand);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 상품 검색 */
    public CustomResponse<List<SecondHand>> searchSecondHand(String keyword) {
        try {
            if (StringUtils.isBlank(keyword)) {
                return CustomResponse.onSuccess(Collections.emptyList());
            }
            List<SecondHand> searchResults = secondHandRepository.findByNameContainingIgnoreCase(keyword);

            return CustomResponse.onSuccess(searchResults);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 상품 판매 상태 변경 (판매중/판매 완료) */
    public CustomResponse<SecondHand> updateSecondHandState(Long shId, char state) {
       try {
           SecondHand secondHand = secondHandRepository.findById(shId)
                   .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

           if(state == 'n') {
               secondHand.setState('y');
           } else if(state == 'y') {
               secondHand.setState('n');
           }
           return CustomResponse.onSuccess(secondHandRepository.saveAndFlush(secondHand));
       } catch (Exception e) {
           return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
       }

    }

    /* 상품 수정 */
    public CustomResponse<SecondHand> updateSecondHand(Principal principal, Long shId, SecondHandRequest request) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 상품을 수정할 수 없습니다.");
            } else {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                if(user.getId() == secondHand.getUserId()) {
                    Category category = categoryRepository.findByCateCode(request.getCateCode());

                    secondHand.setCategory(category);
                    secondHand.setName(request.getName());
                    secondHand.setPrice(request.getPrice());
                    secondHand.setImgUrl(request.getImgUrl());
                    secondHand.setContent(request.getContent());

                    SecondHand updateSecondHand = secondHandRepository.saveAndFlush(secondHand);

                    return CustomResponse.onSuccess(updateSecondHand);
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 상품을 수정할 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 상품 삭제 */
    @Transactional
    public CustomResponse<Void> deleteSecondHand(Principal principal, Long shId) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            if(principal == null) {
                return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 상품을 삭제할 수 없습니다.");
            } else {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                if(user.getId() == secondHand.getUserId()) {
                    secondHandRepository.deleteById(shId);
                    return CustomResponse.onSuccess(null);
                } else {
                    return CustomResponse.onFailure(HttpStatus.METHOD_NOT_ALLOWED.value(), "중고 거래 상품을 삭제할 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 특정 사용자 작성 중고거래 상품 가져오기 */
    public CustomResponse<List<SecondHand>> getSecondHandListByUser (String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        try {
            List<SecondHand> secondHandList = secondHandRepository.findAllByUserId(user.getId());
            return CustomResponse.onSuccess(secondHandList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    public SecondHandResponse setResponse(Principal principal, Long shId) {
        SecondHand secondHand = secondHandRepository.findById(shId)
                .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

        Character favState;
        Character userState;

        if(principal == null) {
            favState = 'n';
            userState = 'n';
        } else {
            String userEmail = principal.getName();
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

            boolean exist = favoriteRepository.existsByUserEmailAndSecondHandId(userEmail, shId);
            if(exist) {
                favState = 'y';
            } else {
                favState = 'n';
            }

            if(user.getId() != (secondHand.getUserId())) {
                userState = 'n';
            } else {
                userState = 'y';
            }
        }

        SecondHandResponse response =
                new SecondHandResponse(secondHand.getUserId(),
                        secondHand.getName(),
                        secondHand.getCategory().getCateCode(),
                        secondHand.getPrice(),
                        secondHand.getImgUrl(),
                        secondHand.getContent(),
                        secondHandBidService.getSecondHandBidList(secondHandBidRepository.findAllBySecondHandId(shId)),
                        favState,
                        userState);

        return response;
    }
}