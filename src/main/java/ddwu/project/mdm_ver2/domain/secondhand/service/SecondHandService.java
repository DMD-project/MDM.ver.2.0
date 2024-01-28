package ddwu.project.mdm_ver2.domain.secondhand.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandRequest;
import ddwu.project.mdm_ver2.global.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.domain.category.repository.CategoryRepository;
import ddwu.project.mdm_ver2.domain.secondhand.repository.SecondHandRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SecondHandService {

    private final SecondHandRepository secondHandRepository;
    private final CategoryRepository categoryRepository;

    /* 전체 상품 */
    public List<SecondHand> findAllProduct() {
        return secondHandRepository.findAll();
    }

    /* 상품 정렬 */
    public List<SecondHand> sortProduct(String sort) {
        switch (sort) {
            case "newest":
                return secondHandRepository.findAllByOrderByShIDDesc();
            default:
                return secondHandRepository.findAll();
        }
    }

    /* 카테고리 내 상품 정렬 */
    public List<SecondHand> sortProductByCategory(String category, String sort) {

        switch (sort) {
            case "newest":
                return secondHandRepository.findAllByCategoryCateCodeOrderByShIDDesc(category);
            case "highprice":
                return secondHandRepository.findAllByCategoryCateCodeOrderByShPriceDesc(category);
            case "lowprice":
                return secondHandRepository.findAllByCategoryCateCodeOrderByShPriceAsc(category);
            default:
                return secondHandRepository.findAllByCategoryCateCode(category);
        }
    }

    /* 상품 상세 정보 */
    public SecondHand getSecondHand(long shID) {
        return secondHandRepository.findByShID(shID);
    }

    /* 상품 등록 */
    public SecondHand addSecondHand(SecondHandRequest request) {

        Category category = categoryRepository.findByCateCode(request.getCateCode());

//        if (category == null) {
//            throw new NotFoundException("카테고리를 찾을 수 없습니다: " + request.getCateCode());
//        }

        SecondHand secondHand = SecondHand.builder()
                .userID(request.getUserID())
                .shName(request.getShName())
                .category(category)
                .shPrice(request.getShPrice())
                .shImg(request.getShImg())
                .shContent(request.getShContent())
                .build();

        return secondHandRepository.saveAndFlush(secondHand);
    }

    /* 상품 검색 */
    public List<SecondHand> searchSecondHand(String keyword) {
        if(StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }
        return secondHandRepository.findByShNameContainingIgnoreCase(keyword);
    }

    /* 상품 판매 상태 변경 (판매중/판매 완료) */

    /* 전체 요청 정렬 */

    /* 상품 수정 */
    public SecondHand updateSecondHand(Long shID, SecondHandRequest secondHandRequest) {
        SecondHand secondHand = secondHandRepository.findByShID(shID);
        if(secondHand == null) {
            throw new ResourceNotFoundException("SecondHand", "shID", shID);
        }

        if(secondHand != null) {
            Category category = categoryRepository.findByCateCode(secondHandRequest.getCateCode());

            secondHand.setCategory(category);
            secondHand.setShName(secondHandRequest.getShName());
            secondHand.setShPrice(secondHandRequest.getShPrice());
            secondHand.setShImg(secondHandRequest.getShImg());
            secondHand.setShContent(secondHandRequest.getShContent());

            return secondHandRepository.saveAndFlush(secondHand);
        }
        return null;
    }

    /* 상품 삭제 */
    @Transactional
    public void deleteSecondHand(Long shID) {
        SecondHand secondHand = secondHandRepository.findByShID(shID);

        if(secondHand == null) {
            throw new ResourceNotFoundException("SecondHand", "shID", shID);
        }

        secondHandRepository.deleteByShID(shID);
    }

}
