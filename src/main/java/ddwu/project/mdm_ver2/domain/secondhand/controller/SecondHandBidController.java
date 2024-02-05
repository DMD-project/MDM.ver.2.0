package ddwu.project.mdm_ver2.domain.secondhand.controller;

import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandRequest;
import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandBidRequest;
import ddwu.project.mdm_ver2.domain.secondhand.service.SecondHandBidService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/secondhand/{shId}/bid")
public class SecondHandBidController implements SecondHandBidApi{

    private final SecondHandBidService shBidService;

    /* 전체 요청 정렬 */
    @GetMapping("sort")
    public CustomResponse<List<SecondHandBid>> getSortedList(@PathVariable("shId") Long shId, @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy) {
        return shBidService.sortShBid(shId, sortBy);
    }

    /* 가격 제안 등록 (댓글 등록) */
    @PostMapping("/add")
    public CustomResponse<SecondHandBid> addShBid(@PathVariable("shId") Long shId,
                                  @RequestBody SecondHandBidRequest request) {
        return shBidService.addShBid(shId, request);
    }

    /* 제안 수정 */
    @PostMapping("/update/{shBidId}")
    public CustomResponse<SecondHandBid> updateShBid(Principal principal,
                                                     @PathVariable("shBidId") Long shBidId, @RequestBody SecondHandBidRequest request) {
        return shBidService.updateShBid(principal.getName(), shBidId, request);
    }

    /* 제안 삭제 */
    @DeleteMapping("/delete/{shBidId}")
    public CustomResponse<Void> deleteShBid(Principal principal,
                                            @PathVariable("shId") Long shId, @PathVariable("shBidId") Long shBidId) {
        return shBidService.deleteSecondHandBid(principal.getName(), shId, shBidId);
    }

}
