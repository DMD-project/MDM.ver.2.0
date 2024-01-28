package ddwu.project.mdm_ver2.domain.secondhand.controller;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandRequest;
import ddwu.project.mdm_ver2.domain.secondhand.service.SecondHandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/secondhand")
public class SecondHandController {

    private final SecondHandService secondHandService;

    /* 전체 상품 */
    public List<SecondHand> getSecondHandList() {
        return secondHandService.findAllProduct();
    }

    /* 상품 정렬 */
    @GetMapping("/list")
    public List<SecondHand> getSortedList(@RequestParam(name="sortBy", required=false, defaultValue="") String sortBy) {
        return secondHandService.sortProduct(sortBy);
    }

    /* 카테고리 내 상품 정렬 */
    @GetMapping("/category/{cateCode}/list")
    public List<SecondHand> getSortedByCategoryList(@PathVariable("cateCode") String cateCode,
                                                    @RequestParam(name="sortBy", required=false, defaultValue="") String sortBy) {
        return secondHandService.sortProductByCategory(sortBy, cateCode);
    }

    /* 상품 상세 정보 */
    @GetMapping("/{shID}")
    public SecondHand getSeconHand(@PathVariable("shID") Long shID) {
        return secondHandService.getSecondHand(shID);
    }

    /* 상품 등록 */
    @PostMapping("/add")
    public SecondHand addSecondHand(@RequestBody SecondHandRequest secondHandRequest) {
        return secondHandService.addSecondHand(secondHandRequest);
    }

    /* 상품 검색 */
    @GetMapping("/search/{keyword}")
    public List<SecondHand> searchSecondHand(@PathVariable("keyword") String keyword) {
        return secondHandService.searchSecondHand(keyword);
    }

    /* 상품 수정 */
    @GetMapping("/update/{shID}")
    public SecondHand updateSecondHand(@PathVariable("shID") Long shID, @RequestBody SecondHandRequest secondHandRequest) {
        return secondHandService.updateSecondHand(shID, secondHandRequest);
    }

    /* 상품 삭제 */
    @GetMapping("/delete/{shID}")
    public void deleteSecondHand(@PathVariable("shID") Long shID) {
        secondHandService.deleteSecondHand(shID);
    }

    /* 상품 판매 상태 변경 (판매중/판매 완료) */


    /* 전체 요청 정렬 */


}
