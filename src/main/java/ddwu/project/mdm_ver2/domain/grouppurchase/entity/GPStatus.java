package ddwu.project.mdm_ver2.domain.grouppurchase.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum GPStatus {
    ONGOING("STATUS_ONGOING", "진행 중"),
    URGENT("STATUS_URGENT", "마감 임박"),
    ACHIEVED("STATUS_ACHIEVED", "공구 성공");

    private final String key;
    private final String title;
}
