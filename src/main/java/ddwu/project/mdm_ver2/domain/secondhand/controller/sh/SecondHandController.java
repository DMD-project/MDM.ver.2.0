package ddwu.project.mdm_ver2.domain.secondhand.controller.sh;

import ddwu.project.mdm_ver2.domain.secondhand.dto.sh.SecondHandResponse;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHand;
import ddwu.project.mdm_ver2.domain.secondhand.dto.sh.SecondHandRequest;
import ddwu.project.mdm_ver2.domain.secondhand.service.SecondHandService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/secondhand")
public class SecondHandController implements SecondHandApi {

    private final SecondHandService secondHandService;

    /* 전체 상품 */
    @GetMapping("/list")
    public CustomResponse<List<SecondHand>> getSecondHandList() {
        return secondHandService.findAllSecondHand();
    }

    /* 상품 정렬 */
    @GetMapping("/sort/category")
    public CustomResponse<List<SecondHand>> getSortedList(@RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
                                                          @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        return secondHandService.sortSecondHand(sortBy, cateCode);
    }

    /* 상품 상세 정보 */
    @GetMapping("/{shId}")
    public CustomResponse<SecondHandResponse> getSeconHand(Principal principal, @PathVariable("shId") Long shId) {
        return secondHandService.getSecondHand(principal, shId);
    }

    /* 상품 등록 */
    @PostMapping("/add")
    public CustomResponse<SecondHand> addSecondHand(@RequestBody SecondHandRequest request) {
        return secondHandService.addSecondHand(request);
    }

    /* 상품 검색 */
    @GetMapping("/search/{keyword}")
    public List<SecondHand> searchSecondHand(@PathVariable("keyword") String keyword) {
        return secondHandService.searchSecondHand(keyword);
    }

    /* 상품 수정 */
    @PutMapping("/update/{shId}")
    public CustomResponse<SecondHand> updateSecondHand(Principal principal, @PathVariable("shId") Long shId, @RequestBody SecondHandRequest request) {
        return secondHandService.updateSecondHand(principal, shId, request);
    }

    /* 상품 판매 상태 변경 (판매중/판매 완료) */
    @PostMapping("/update/{shId}/state/{state}")
    public CustomResponse<SecondHand> updateSecondHandState(@PathVariable("shId") Long shId, @PathVariable("state") char state) {
        return secondHandService.updateSecondHandState(shId, state);
    }

    /* 상품 삭제 */
    @DeleteMapping("/delete/{shId}")
    public CustomResponse<Void> deleteSecondHand(Principal principal, @PathVariable("shId") Long shId) {
        return secondHandService.deleteSecondHand(principal, shId);
    }

}
