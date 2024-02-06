package ddwu.project.mdm_ver2.domain.secondhand.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.favorite.repository.FavoriteRepository;
import ddwu.project.mdm_ver2.domain.secondhand.dto.sh.SecondHandResponse;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.dto.sh.SecondHandRequest;
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
                sortedSecondHandList = secondHandRepository.findAllByCategoryCateCodeOrderByPriceAsc(cateCode);
            case "highprice":
                sortedSecondHandList = secondHandRepository.findAllByCategoryCateCodeOrderByPriceDesc(cateCode);
            case "newest":
                sortedSecondHandList = secondHandRepository.findAllByCategoryCateCodeOrderByIdDesc(cateCode);
            default:
                sortedSecondHandList = secondHandRepository.findAllByCategoryCateCode(cateCode);
        }
        return sortedSecondHandList;
    }

    /* 상품 상세 정보 */
    public CustomResponse<SecondHandResponse> getSecondHand(Principal principal, Long shId) {
        try {
            SecondHand secondHand = secondHandRepository.findById(shId)
                    .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

            SecondHandResponse response;

            if(principal == null) {
                response = setResponse(shId, 'n', 'n');

            } else {
                String userEmail = principal.getName();

                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                boolean exist = favoriteRepository.existsByUserEmailAndSecondHandId(userEmail, shId);
                Character favState;

                if(exist) {
                    favState = 'y';
                } else {
                    favState = 'n';
                }

                if(user.getId() != (secondHand.getUserId())) {
                    response = setResponse(shId, favState, 'n');
                } else {
                    response = setResponse(shId, favState, 'y');
                }
            }

            return CustomResponse.onSuccess(response);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    /* 상품 등록 */
    public CustomResponse<SecondHand> addSecondHand(SecondHandRequest request) {
        try {
            Category category = categoryRepository.findByCateCode(request.getCateCode());

            if (category == null) {
                throw new NotFoundException("카테고리를 찾을 수 없습니다: " + request.getCateCode());
            }

            SecondHand secondHand = SecondHand.builder()
                    .userId(request.getUserId())
                    .name(request.getName())
                    .category(category)
                    .price(request.getPrice())
                    .imgUrl(request.getImgUrl())
                    .content(request.getContent())
                    .build();

            SecondHand newSecondHand = secondHandRepository.saveAndFlush(secondHand);
            return CustomResponse.onSuccess(newSecondHand);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /* 상품 검색 */
    public List<SecondHand> searchSecondHand(String keyword) {
        if(StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        return secondHandRepository.findByNameContainingIgnoreCase(keyword);
    }

    /* 상품 판매 상태 변경 (판매중/판매 완료) */
    public CustomResponse<SecondHand> updateSecondHandState(Long shId, char state) {
       try {
           SecondHand secondHand = secondHandRepository.findById(shId)
                   .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

           if(secondHand != null) {
               secondHand.setState(state);
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
                String userEmail = principal.getName();

                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

                if(user.getId() == request.getUserId()) {
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
                String userEmail = principal.getName();

                User user = userRepository.findByEmail(userEmail)
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

    public SecondHandResponse setResponse(Long shId, Character favState, Character userState) {
        SecondHand secondHand = secondHandRepository.findById(shId)
                .orElseThrow(() -> new NotFoundException("중고거래 상품을 찾을 수 없습니다."));

        SecondHandResponse response =
                new SecondHandResponse(secondHand.getUserId(),
                        secondHand.getName(),
                        secondHand.getCategory().getCateCode(),
                        secondHand.getPrice(),
                        secondHand.getImgUrl(),
                        secondHand.getContent(),
                        secondHandBidRepository.findAllBySecondHandId(shId),
                        favState,
                        userState);

        return response;
    }
}
