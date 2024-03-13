package ddwu.project.mdm_ver2.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자"),
    GENERAL("ROLE_GENERAL", "일반");

    private final String key;
    private final String title;
}