package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.SecondHandReq;
import ddwu.project.mdm_ver2.dto.SecondHandReqRequest;
import ddwu.project.mdm_ver2.service.SecondHandReqService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/secondhand/{shID}")
public class SecondHandReqController {

    private final SecondHandReqService shReqService;

    /* 가격 제안 등록 (댓글 등록) */
    public SecondHandReq addShReq(@PathVariable("shID") Long shID,
                                  @RequestBody SecondHandReqRequest shReqRequest) {
        return shReqService.addShReq(shID, shReqRequest);
    }

    /* 제안 수정 */

    /* 제안 삭제 */
}
