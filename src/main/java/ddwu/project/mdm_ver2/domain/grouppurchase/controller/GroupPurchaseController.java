package ddwu.project.mdm_ver2.domain.grouppurchase.controller;

import ddwu.project.mdm_ver2.domain.grouppurchase.dto.GroupPurchaseDto;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.service.GroupPurchaseService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/gp")
public class GroupPurchaseController implements GroupPurcahseApi {

    private final GroupPurchaseService groupPurchaseService;

    /* 공동구매 목록 조회 */
    @GetMapping("/list")
    public CustomResponse<List<GroupPurchase>> getGroupPurchaseList() {
        return groupPurchaseService.findAllGroupPurchases();
    }

    /* 공동구매 높은 가격순, 높은 가격순, 최신순 정렬 */
    @GetMapping("/sort/category")
    public CustomResponse<List<GroupPurchase>> getSortedList(
            @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
            @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        return groupPurchaseService.sortGroupPurchase(sortBy, cateCode);
    }

    /* 특정 공동구매 상품 조회 */
    @GetMapping("/{gpId}")
    public CustomResponse<GroupPurchaseDto> getGroupPurchase(@PathVariable Long gpId) {
        return groupPurchaseService.getGroupPurchase(gpId);
    }

    /* 공동구매 전체 개수 */
    @GetMapping("/count")
    public CustomResponse<Long> countAllGroupPurchases() {
        return groupPurchaseService.countAllGroupPurchases();
    }

    /* 공동구매 참여 */
    @PostMapping("/order/{gpId}/{purchasedQty}")
    public CustomResponse<String> joinGroupPurchase(Principal principal, @PathVariable Long gpId, @PathVariable int purchasedQty) {
        return groupPurchaseService.joinGroupPurchase(principal, gpId, purchasedQty);
    }

    /* 공동구매 검색 */
    @GetMapping("/search/{keyword}")
    public CustomResponse<List<GroupPurchase>> searchGroupPurchase(@RequestParam(name = "keyword") String keyword) {
        return groupPurchaseService.searchGroupPurchase(keyword);
    }
}