package ddwu.project.mdm_ver2.domain.grouppurchase.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GPStatus {
    ONGOING("ONGOING", "진행 중"),
    URGENT("URGENT", "마감 임박"),
    ACHIEVED("ACHIEVED", "공구 성공"),
    FAIL("FAIL", "공구 실패");

    private final String key;
    private final String title;
}
