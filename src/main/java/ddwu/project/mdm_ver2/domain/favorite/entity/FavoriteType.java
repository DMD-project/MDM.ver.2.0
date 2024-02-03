package ddwu.project.mdm_ver2.domain.favorite.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FavoriteType {

    GENERAL("TYPE_GENERAL", "일반상품"),
    GP("TYPE_GP", "공동 구매 상품"),
    SH("TYPE_SH", "중고 거래 상품");

    private final String key;
    private final String title;
}
