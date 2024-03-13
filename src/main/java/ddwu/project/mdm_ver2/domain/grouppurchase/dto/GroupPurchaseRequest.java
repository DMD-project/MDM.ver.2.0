package ddwu.project.mdm_ver2.domain.grouppurchase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupPurchaseRequest {
    private String name;
    private int price;
    private String content;
    private String imgUrl;
    private int maxQty;
    private int goalQty;
    private LocalDate start;
    private LocalDate end;
    private String cateCode;
}