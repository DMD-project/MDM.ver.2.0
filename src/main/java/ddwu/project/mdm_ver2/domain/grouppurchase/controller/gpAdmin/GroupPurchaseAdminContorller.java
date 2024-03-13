package ddwu.project.mdm_ver2.domain.grouppurchase.controller.gpAdmin;

import ddwu.project.mdm_ver2.domain.grouppurchase.dto.GroupPurchaseRequest;
import ddwu.project.mdm_ver2.domain.grouppurchase.service.GroupPurchaseService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/gp")
public class GroupPurchaseAdminContorller implements GroupPurchaseAdminApi {

    private final GroupPurchaseService groupPurchaseService;

    /* 공동구매 등록 */
    @PostMapping("/add")
    public CustomResponse<Void> addGroupPurchase(@RequestBody GroupPurchaseRequest request) {
        return groupPurchaseService.addGroupPurchase(request);
    }

    /* 공동구매 수정 */
    @PutMapping("/update/{gpId}")
    public CustomResponse<Void> updateGroupPurchase(@PathVariable Long gpId, @RequestBody GroupPurchaseRequest request) {
        return groupPurchaseService.updateGroupPurchase(gpId, request);
    }

    /* 공동구매 삭제 */
    @DeleteMapping("/delete/{gpId}")
    public CustomResponse<Void> deleteGroupPurchase(@PathVariable Long gpId) {
        return groupPurchaseService.deleteGroupPurchase(gpId);
    }
}