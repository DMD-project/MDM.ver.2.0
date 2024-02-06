package ddwu.project.mdm_ver2.domain.grouppurchase.controller;

import ddwu.project.mdm_ver2.domain.grouppurchase.controller.GroupPurcahseApi;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchase;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchaseParticipant;
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

    // 공동구매 목록 조회
    @GetMapping("/list")
    public CustomResponse<List<GroupPurchase>> getGroupPurchaseList() {
        return groupPurchaseService.findAllGroupPurchases();
    }

    // 공동구매 정렬 높은 가격순, 높은 가겨순, 최신순
    @GetMapping("/sort/category")
    public CustomResponse<List<GroupPurchase>> getSortedList(
            @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
            @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        return groupPurchaseService.sortGroupPurchase(sortBy, cateCode);
    }


        // 특정 공동구매상품 조회
        @GetMapping("/{gpId}")
        public CustomResponse<List<GroupPurchaseParticipant>> getGroupPurchase (@PathVariable Long gpId){
            return groupPurchaseService.getGroupPurchase(gpId);
        }

        //특정 사용자가 참여한 모든 공동구매
        @GetMapping("/participants")
        public CustomResponse<List<GroupPurchase>> getGroupPurchasesByUser (Principal principal){
            return groupPurchaseService.getGroupPurchasesByUser(principal.getName());
        }

        // 공동구매 전체 개수
        @GetMapping("/count")
        public CustomResponse<Long> countAllGroupPurchases () {
            return groupPurchaseService.countAllGroupPurchases();
        }


        // 공동구매 참여
        @PostMapping("/order/{gpId}/{purchasedQty}")
        public CustomResponse<String> joinGroupPurchase (@PathVariable Long gpId,
        @PathVariable int purchasedQty, Principal principal){
            return groupPurchaseService.joinGroupPurchase(gpId, principal.getName(), purchasedQty);
        }

        // 공동구매 취소
        @DeleteMapping("/cancel/{gpId}")
        public CustomResponse<String> cancelGroupPurchase (@PathVariable Long gpId, Principal principal){
            return groupPurchaseService.cancelGroupPurchase(gpId, principal.getName());
        }

        //  공동구매 검색
        @GetMapping("/search/{keyword}")
        public CustomResponse<List<GroupPurchase>> searchGroupPurchase (@RequestParam(name = "keyword") String keyword){
            return groupPurchaseService.searchGroupPurchase(keyword);
        }
//
//    // 공동구매 결제
//    @PostMapping("/pay/{gpId}")
//    public CustomResponse<Void> payGroupPurchase(@PathVariable Long gpId, Principal principal) {
//        return groupPurchaseService.payGroupPurchase(gpId, principal.getName());
//    }

    }
