package ddwu.project.mdm_ver2.domain.secondhand.controller;

import ddwu.project.mdm_ver2.domain.secondhand.entity.SecondHandBid;
import ddwu.project.mdm_ver2.domain.secondhand.dto.SecondHandBidRequest;
import ddwu.project.mdm_ver2.domain.secondhand.service.SecondHandBidService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/secondhand/{shID}")
public class SecondHandBidController {

    private final SecondHandBidService shReqService;

    /* 가격 제안 등록 (댓글 등록) */
    public SecondHandBid addShReq(@PathVariable("shID") Long shID,
                                  @RequestBody SecondHandBidRequest shReqRequest) {
        return shReqService.addShReq(shID, shReqRequest);
    }

    /* 제안 수정 */

    /* 제안 삭제 */
}
